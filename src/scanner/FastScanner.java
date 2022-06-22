package scanner;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class FastScanner implements Closeable {
    Reader in;
    char[] buffer;
    int read;
    int length;
    int ptr;
    int markPtr;

    public static enum TokenClass {
        WORD {
            @Override
            public boolean is(char ch) {
                return Character.getType(ch) == Character.DASH_PUNCTUATION || ch == '\'' || Character.isLetter(ch);
            }
        },
        NUMBER {
            private boolean isHexLetter(char ch) {
                if ((ch - 'A' >= 0 && ch - 'A' <= 5) || ch == 'X') {
                    return true;
                }
                if ((ch - 'a' >= 0 && ch - 'a' <= 5) || ch == 'x') {
                    return true;
                }
                return false;
            }

            @Override
            public boolean is(char ch) {
                return Character.getType(ch) == Character.DECIMAL_DIGIT_NUMBER || ch == '-' || this.isHexLetter(ch);
            }
        };

        abstract boolean is(char ch);
    }

    private static final int DEFAULT_BUFFER = 256;
    private static final int INT_NULLPTR = -1;
    private static final char CR = '\r';
    private static final char LF = '\n';

    public FastScanner(InputStream in, Charset encoding) {
        this.in = new InputStreamReader(in, encoding);
        this.buffer = new char[DEFAULT_BUFFER];
        this.read = 0;
        this.length = this.ptr = 0;
        this.markPtr = INT_NULLPTR;
    }

    public FastScanner(InputStream in) {
        this(in, StandardCharsets.UTF_8);
    }

    public FastScanner(String str, Charset encoding) {
        this(new ByteArrayInputStream(str.getBytes()), encoding);
    }

    public FastScanner(String str) {
        this(str, StandardCharsets.UTF_8);
    }

    public String nextToken(TokenClass tokenClass, boolean inLine) {
        this.ensureOpen();
        try {
            StringBuilder token = new StringBuilder();
            if (this.bufferEnded()) {
                this.bufferRead();
            }
            if (this.inputEnded()) {
                return null;
            }
            while (!tokenClass.is(this.buffer[this.ptr])) {
                if (inLine && FastScanner.isEndLineChar(this.buffer[this.ptr])) {
                    return null;
                }
                this.ptr++;
                if (this.bufferEnded()) {
                    this.bufferRead();
                }
                if (this.inputEnded()) {
                    return null;
                }
            }
            while (tokenClass.is(this.buffer[this.ptr])) {
                token.append(this.buffer[this.ptr++]);
                if (this.bufferEnded()) {
                    this.bufferRead();
                }
                if (this.inputEnded()) {
                    break;
                }
            }
            return token.length() > 0 ? token.toString() : null;
        } catch (IOException exc) {
            System.err.println("Bad tokinazing: " + exc.getMessage());
        }
        return null;
    }

    private String nextWord(boolean inLine) {
        this.ensureOpen();
        return this.nextToken(FastScanner.TokenClass.WORD, inLine);
    }

    public String nextWord() {
        return this.nextWord(false);
    }

    public String nextWordInLine() {
        return this.nextWord(true);
    }

    private boolean hasNextWord(boolean inLine) {
        this.ensureOpen();
        try {
            this.mark();
            return this.nextWord(inLine) != null;
        } finally {
            this.reset();
        }
    }

    public boolean hasNextWord() {
        return this.hasNextWord(false);
    }

    public boolean hasNextWordInLine() {
        return this.hasNextWord(true);
    }

    private Integer nextInt(boolean inLine) {
        this.ensureOpen();
        try {
            String nextToken = nextToken(FastScanner.TokenClass.NUMBER, inLine);
            if (nextToken == null) {
                return null;
            }
            if (nextToken.toLowerCase().startsWith("0x")) {
                return Integer.parseUnsignedInt(nextToken.substring(2), 16);
            } else {
                return Integer.parseInt(nextToken);
            }
        } catch (NumberFormatException exc) {
            System.err.println("Bad int parsing: " + exc.getMessage());
        }
        return null;
    }

    public Integer nextInt() {
        return this.nextInt(false);
    }

    public Integer nextIntInLine() {
        return this.nextInt(true);
    }

    private boolean hasNextInt(boolean inLine) {
        this.ensureOpen();
        this.mark();
        try {
            String nextToken = this.nextToken(FastScanner.TokenClass.NUMBER, inLine);
            return nextToken != null && FastScanner.isInt(nextToken);
        } catch (NoSuchElementException ext) {
            return false;
        } finally {
            this.reset();
        }
    }

    public boolean hasNextInt() {
        return this.hasNextInt(false);
    }

    public boolean hasNextIntInLine() {
        return this.hasNextInt(true);
    }

    private static boolean isInt(String str) {
        try {
            str = str.toLowerCase();
            if (str.startsWith("0x")) {
                Integer.parseUnsignedInt(str.substring(2), 16);
            } else {
                Integer.parseInt(str);
            }
            return true;
        } catch (NumberFormatException exc) {
            return false;
        }
    }

    private static boolean isEndLineChar(char ch) {
        return ch == FastScanner.CR || ch == FastScanner.LF;
    }

    private String nextLine(boolean write) {
        this.ensureOpen();
        try {
            StringBuilder sb = new StringBuilder();
            if (this.bufferEnded()) {
                this.bufferRead();
            }
            while (true) {
                char ch = this.buffer[this.ptr++];
                if (write && (ch != FastScanner.CR || ch != FastScanner.LF)) {
                    sb.append(ch);
                }
                if (this.bufferEnded()) {
                    this.bufferRead();
                }
                if (ch == FastScanner.LF || this.inputEnded()) {
                    break;
                }
                if (ch == FastScanner.CR) {
                    if (this.buffer[this.ptr] == FastScanner.LF) {
                        this.ptr++;
                    }
                    break;
                }
            }
            return sb.toString();
        } catch (IOException exc) {
            System.err.println("Bad line reading: " + exc.getMessage());
        }
        return null;
    }

    public String nextLine() {
        return this.nextLine(true);
    }

    public void moveToNextLine() {
        this.nextLine(false);
    }

    public boolean hasNextLine() {
        this.ensureOpen();
        if (this.bufferEnded()) {
            bufferEnded();
        }
        if (this.inputEnded()) {
            return false;
        }
        return true;
    }

    private void bufferRead() throws IOException {
        if (markPtr == INT_NULLPTR && buffer.length > DEFAULT_BUFFER) {
            buffer = new char[DEFAULT_BUFFER];
        }

        if (markPtr == INT_NULLPTR) {
            this.read = this.in.read(buffer);
            this.length = this.read != INT_NULLPTR ? this.read : 0;
            this.ptr = 0;
        } else {
            char[] newBuffer = new char[buffer.length + this.length - this.markPtr];
            System.arraycopy(this.buffer, this.markPtr, newBuffer, 0, this.length - this.markPtr);
            this.ptr = this.length - this.markPtr;
            this.read = this.in.read(buffer);
            this.length = this.ptr + (this.read != INT_NULLPTR ? this.read : 0);
            System.arraycopy(this.buffer, 0, newBuffer, this.ptr, this.buffer.length);
            this.markPtr = 0;
            this.buffer = newBuffer;
        }
    }

    private void mark() {
        this.markPtr = this.ptr;
    }

    private void reset() {
        this.ptr = this.markPtr;
        this.markPtr = INT_NULLPTR;
    }

    private boolean bufferEnded() {
        return this.ptr >= this.length;
    }

    private boolean inputEnded() {
        return this.bufferEnded() && this.read == INT_NULLPTR;
    }

    public void close() {
        if (this.in == null) {
            System.err.println("Scaner already closed");
        }
        try {
            this.in.close();
        } catch (IOException exc) {
            System.err.println("Bad scanner closing: " + exc.getMessage());
        } finally {
            this.in = null;
        }
    }

    private void ensureOpen() {
        if (this.in == null) {
            throw new IllegalStateException("MyScanner is closed.");
        }
    }
}

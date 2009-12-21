/* The following code was generated by JFlex 1.4.3 on 12/20/09 8:55 PM */
package src;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 12/20/09 8:55 PM from the specification file
 * <tt>Breezy.flex</tt>
 */
class Yylex {

    /** This character denotes the end of file */
    public static final int YYEOF = -1;
    /** initial size of the lookahead buffer */
    private static final int ZZ_BUFFERSIZE = 16384;
    /** lexical states */
    public static final int YYINITIAL = 0;
    /**
     * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
     * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
     *                  at the beginning of a line
     * l is of the form l = 2*k, k a non negative integer
     */
    private static final int ZZ_LEXSTATE[] = {
        0, 0
    };
    /**
     * Translates characters to character classes
     */
    private static final String ZZ_CMAP_PACKED =
            "\11\0\1\4\1\3\1\0\1\4\1\2\22\0\1\4\1\0\1\66" +
            "\2\0\1\35\2\0\1\54\1\55\1\6\1\33\1\62\1\34\1\63" +
            "\1\5\12\1\1\60\1\61\1\36\1\37\1\40\2\0\1\44\2\64" +
            "\1\50\1\41\6\64\1\45\1\64\1\47\1\51\1\64\1\42\1\52" +
            "\1\46\1\53\1\43\5\64\1\56\1\67\1\57\1\0\1\65\1\0" +
            "\1\7\1\15\1\10\1\27\1\11\1\26\1\16\1\23\1\17\2\64" +
            "\1\25\1\31\1\20\1\24\1\12\1\64\1\21\1\14\1\13\1\30" +
            "\1\64\1\32\1\64\1\22\1\64\uff85\0";
    /**
     * Translates characters to character classes
     */
    private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);
    /**
     * Translates DFA states to action switch labels.
     */
    private static final int[] ZZ_ACTION = zzUnpackAction();
    private static final String ZZ_ACTION_PACKED_0 =
            "\1\1\1\2\1\1\2\3\1\4\1\5\1\6\15\7" +
            "\1\10\1\11\1\12\1\13\1\14\1\15\4\7\1\16" +
            "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\2" +
            "\10\0\7\7\1\26\1\27\10\7\1\30\1\31\3\7" +
            "\1\32\1\1\1\0\1\33\5\0\2\34\2\0\14\7" +
            "\1\35\3\7\1\36\1\37\1\33\1\0\1\40\4\0" +
            "\1\7\1\41\1\42\1\7\1\43\5\7\1\44\4\7" +
            "\3\0\1\45\1\0\1\46\5\7\1\47\1\7\1\50" +
            "\1\7\1\0\1\51\2\0\1\52\2\7\1\53\1\54" +
            "\1\7\1\55\2\0\1\56\1\57\1\60\1\7\1\61" +
            "\1\62\1\63";

    private static int[] zzUnpackAction() {
        int[] result = new int[158];
        int offset = 0;
        offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackAction(String packed, int offset, int[] result) {
        int i = 0;       /* index in packed string  */
        int j = offset;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }
    /**
     * Translates a state to a row index in the transition table
     */
    private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
    private static final String ZZ_ROWMAP_PACKED_0 =
            "\0\0\0\70\0\160\0\250\0\340\0\70\0\u0118\0\70" +
            "\0\u0150\0\u0188\0\u01c0\0\u01f8\0\u0230\0\u0268\0\u02a0\0\u02d8" +
            "\0\u0310\0\u0348\0\u0380\0\u03b8\0\u03f0\0\70\0\160\0\70" +
            "\0\u0428\0\70\0\u0460\0\u0498\0\u04d0\0\u0508\0\u0540\0\70" +
            "\0\70\0\70\0\70\0\70\0\70\0\70\0\u0578\0\u05b0" +
            "\0\u0578\0\u05e8\0\u0620\0\u0658\0\u0690\0\u06c8\0\u0700\0\u0738" +
            "\0\u0770\0\u07a8\0\u07e0\0\u0818\0\u0850\0\u0888\0\u08c0\0\u0188" +
            "\0\u0188\0\u08f8\0\u0930\0\u0968\0\u09a0\0\u09d8\0\u0a10\0\u0a48" +
            "\0\u0a80\0\70\0\70\0\u0ab8\0\u0af0\0\u0b28\0\u0188\0\u0578" +
            "\0\u05b0\0\70\0\u0b60\0\u0b98\0\u0bd0\0\u0c08\0\u0c40\0\u0c78" +
            "\0\70\0\u0cb0\0\u0ce8\0\u0d20\0\u0d58\0\u0d90\0\u0dc8\0\u0e00" +
            "\0\u0e38\0\u0e70\0\u0ea8\0\u0ee0\0\u0f18\0\u0f50\0\u0f88\0\u0188" +
            "\0\u0fc0\0\u0ff8\0\u1030\0\u0188\0\u0188\0\u05b0\0\u1068\0\70" +
            "\0\u10a0\0\u10d8\0\u1110\0\u1148\0\u1180\0\u0188\0\u11b8\0\u11f0" +
            "\0\u0188\0\u1228\0\u1260\0\u1298\0\u12d0\0\u1308\0\u0188\0\u1340" +
            "\0\u1378\0\u13b0\0\u13e8\0\u1420\0\u1458\0\u1490\0\u0188\0\u14c8" +
            "\0\u0188\0\u1500\0\u1538\0\u1570\0\u15a8\0\u15e0\0\u0188\0\u1618" +
            "\0\u0188\0\u1650\0\u1688\0\70\0\u16c0\0\u16f8\0\u0188\0\u1730" +
            "\0\u1768\0\u0188\0\u0188\0\u17a0\0\u0188\0\u17d8\0\u1810\0\70" +
            "\0\u0188\0\u0188\0\u1848\0\70\0\70\0\u0188";

    private static int[] zzUnpackRowMap() {
        int[] result = new int[158];
        int offset = 0;
        offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackRowMap(String packed, int offset, int[] result) {
        int i = 0;  /* index in packed string  */
        int j = offset;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int high = packed.charAt(i++) << 16;
            result[j++] = high | packed.charAt(i++);
        }
        return j;
    }
    /**
     * The transition table of the DFA
     */
    private static final int[] ZZ_TRANS = zzUnpackTrans();
    private static final String ZZ_TRANS_PACKED_0 =
            "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11" +
            "\1\12\1\13\1\14\1\15\1\16\1\17\1\12\1\20" +
            "\1\21\1\22\1\12\1\23\2\12\1\24\3\12\1\25" +
            "\1\26\1\27\1\30\1\31\1\32\1\33\1\34\2\12" +
            "\1\35\2\12\1\36\1\12\1\37\2\12\1\40\1\41" +
            "\1\42\1\43\1\44\1\45\1\46\1\47\1\12\1\2" +
            "\1\50\1\2\71\0\1\3\61\0\1\51\7\0\1\5" +
            "\1\52\2\0\1\53\1\0\1\54\3\0\1\55\3\0" +
            "\1\56\52\0\1\52\2\0\1\53\1\0\1\54\3\0" +
            "\1\55\3\0\1\56\53\0\1\57\1\60\62\0\1\12" +
            "\5\0\12\12\1\61\11\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\24\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\1\62\15\12\1\63\5\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\12\12\1\64" +
            "\11\12\6\0\13\12\10\0\2\12\3\0\1\12\5\0" +
            "\12\12\1\65\11\12\6\0\13\12\10\0\2\12\3\0" +
            "\1\12\5\0\4\12\1\66\17\12\6\0\13\12\10\0" +
            "\2\12\3\0\1\12\5\0\15\12\1\67\6\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\11\12\1\70" +
            "\5\12\1\71\4\12\6\0\13\12\10\0\2\12\3\0" +
            "\1\12\5\0\15\12\1\72\3\12\1\73\2\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\2\12\1\74" +
            "\21\12\6\0\13\12\10\0\2\12\3\0\1\12\5\0" +
            "\1\75\23\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\5\0\1\76\14\12\1\77\3\12\1\100\2\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\14\12\1\101" +
            "\7\12\6\0\13\12\10\0\2\12\41\0\1\102\67\0" +
            "\1\103\31\0\1\12\5\0\24\12\6\0\1\12\1\104" +
            "\11\12\10\0\2\12\3\0\1\12\5\0\24\12\6\0" +
            "\6\12\1\105\4\12\10\0\2\12\3\0\1\12\5\0" +
            "\24\12\6\0\10\12\1\106\2\12\10\0\2\12\3\0" +
            "\1\12\5\0\24\12\6\0\11\12\1\107\1\12\10\0" +
            "\2\12\3\0\1\110\66\0\2\111\2\0\62\111\1\112" +
            "\1\113\4\0\1\52\4\0\1\54\3\0\1\55\62\0" +
            "\1\114\77\0\1\115\60\0\1\116\67\0\1\117\56\0" +
            "\2\57\1\120\1\121\64\57\6\122\1\123\61\122\1\0" +
            "\1\12\5\0\12\12\1\124\11\12\6\0\13\12\10\0" +
            "\2\12\3\0\1\12\5\0\1\12\1\125\22\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\5\12\1\126" +
            "\16\12\6\0\13\12\10\0\2\12\3\0\1\12\5\0" +
            "\10\12\1\127\13\12\6\0\13\12\10\0\2\12\3\0" +
            "\1\12\5\0\21\12\1\130\2\12\6\0\13\12\10\0" +
            "\2\12\3\0\1\12\5\0\12\12\1\131\11\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\15\12\1\132" +
            "\6\12\6\0\13\12\10\0\2\12\3\0\1\12\5\0" +
            "\4\12\1\133\17\12\6\0\13\12\10\0\2\12\3\0" +
            "\1\12\5\0\22\12\1\134\1\12\6\0\13\12\10\0" +
            "\2\12\3\0\1\12\5\0\4\12\1\135\17\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\5\12\1\136" +
            "\16\12\6\0\13\12\10\0\2\12\3\0\1\12\5\0" +
            "\16\12\1\137\5\12\6\0\13\12\10\0\2\12\3\0" +
            "\1\12\5\0\12\12\1\140\11\12\6\0\13\12\10\0" +
            "\2\12\3\0\1\12\5\0\11\12\1\141\12\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\10\12\1\142" +
            "\13\12\6\0\13\12\10\0\2\12\3\0\1\12\5\0" +
            "\24\12\6\0\2\12\1\143\10\12\10\0\2\12\3\0" +
            "\1\12\5\0\24\12\6\0\7\12\1\144\3\12\10\0" +
            "\2\12\3\0\1\12\5\0\24\12\6\0\12\12\1\145" +
            "\10\0\2\12\2\0\2\111\2\0\62\111\1\146\1\113" +
            "\10\0\1\147\106\0\1\150\56\0\1\151\64\0\1\152" +
            "\57\0\1\121\64\0\6\122\1\153\61\122\5\0\1\154" +
            "\1\123\62\0\1\12\5\0\1\155\23\12\6\0\13\12" +
            "\10\0\2\12\3\0\1\12\5\0\14\12\1\156\7\12" +
            "\6\0\13\12\10\0\2\12\3\0\1\12\5\0\2\12" +
            "\1\157\21\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\5\0\11\12\1\160\12\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\2\12\1\161\21\12\6\0\13\12" +
            "\10\0\2\12\3\0\1\12\5\0\10\12\1\162\13\12" +
            "\6\0\13\12\10\0\2\12\3\0\1\12\5\0\16\12" +
            "\1\163\5\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\5\0\14\12\1\164\7\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\6\12\1\165\15\12\6\0\13\12" +
            "\10\0\2\12\3\0\1\12\5\0\21\12\1\166\2\12" +
            "\6\0\13\12\10\0\2\12\3\0\1\12\5\0\14\12" +
            "\1\167\7\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\5\0\5\12\1\170\16\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\1\12\1\171\22\12\6\0\13\12" +
            "\10\0\2\12\3\0\1\12\5\0\16\12\1\172\5\12" +
            "\6\0\13\12\10\0\2\12\3\0\1\12\5\0\24\12" +
            "\6\0\3\12\1\173\7\12\10\0\2\12\13\0\1\174" +
            "\75\0\1\175\100\0\1\176\37\0\5\122\1\154\1\153" +
            "\61\122\2\0\1\120\1\121\65\0\1\12\5\0\13\12" +
            "\1\177\10\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\2\0\1\200\2\0\24\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\4\12\1\201\17\12\6\0\13\12" +
            "\10\0\2\12\3\0\1\12\5\0\11\12\1\202\12\12" +
            "\6\0\13\12\10\0\2\12\3\0\1\12\5\0\2\12" +
            "\1\203\21\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\5\0\10\12\1\204\13\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\2\12\1\205\21\12\6\0\13\12" +
            "\10\0\2\12\3\0\1\12\5\0\12\12\1\206\11\12" +
            "\6\0\13\12\10\0\2\12\3\0\1\12\5\0\2\12" +
            "\1\207\21\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\5\0\4\12\1\210\17\12\6\0\13\12\10\0\2\12" +
            "\3\0\1\12\5\0\2\12\1\211\21\12\6\0\13\12" +
            "\10\0\2\12\3\0\1\12\5\0\24\12\6\0\4\12" +
            "\1\212\6\12\10\0\2\12\14\0\1\213\75\0\1\214" +
            "\70\0\1\215\65\0\1\216\51\0\1\12\5\0\7\12" +
            "\1\217\14\12\6\0\13\12\10\0\2\12\3\0\1\12" +
            "\5\0\1\220\23\12\6\0\13\12\10\0\2\12\3\0" +
            "\1\12\5\0\11\12\1\221\12\12\6\0\13\12\10\0" +
            "\2\12\3\0\1\12\5\0\12\12\1\222\11\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\11\12\1\223" +
            "\12\12\6\0\13\12\10\0\2\12\3\0\1\12\5\0" +
            "\10\12\1\224\13\12\6\0\13\12\10\0\2\12\3\0" +
            "\1\12\5\0\24\12\6\0\5\12\1\225\5\12\10\0" +
            "\2\12\15\0\1\226\74\0\1\227\75\0\1\230\42\0" +
            "\1\12\5\0\11\12\1\231\12\12\6\0\13\12\10\0" +
            "\2\12\3\0\1\12\5\0\7\12\1\232\14\12\6\0" +
            "\13\12\10\0\2\12\3\0\1\12\5\0\15\12\1\233" +
            "\6\12\6\0\13\12\10\0\2\12\16\0\1\234\67\0" +
            "\1\235\54\0\1\12\5\0\11\12\1\236\12\12\6\0" +
            "\13\12\10\0\2\12\2\0";

    private static int[] zzUnpackTrans() {
        int[] result = new int[6272];
        int offset = 0;
        offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackTrans(String packed, int offset, int[] result) {
        int i = 0;       /* index in packed string  */
        int j = offset;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            value--;
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }
    /* error codes */
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;

    /* error messages for the codes above */
    private static final String ZZ_ERROR_MSG[] = {
        "Unkown internal scanner error",
        "Error: could not match input",
        "Error: pushback value was too large"
    };
    /**
     * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
     */
    private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
    private static final String ZZ_ATTRIBUTE_PACKED_0 =
            "\1\1\1\11\3\1\1\11\1\1\1\11\15\1\1\11" +
            "\1\1\1\11\1\1\1\11\5\1\7\11\2\1\10\0" +
            "\21\1\2\11\5\1\1\0\1\11\5\0\1\1\1\11" +
            "\2\0\23\1\1\0\1\11\4\0\17\1\3\0\1\1" +
            "\1\0\12\1\1\0\1\11\2\0\7\1\2\0\1\11" +
            "\3\1\2\11\1\1";

    private static int[] zzUnpackAttribute() {
        int[] result = new int[158];
        int offset = 0;
        offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
        return result;
    }

    private static int zzUnpackAttribute(String packed, int offset, int[] result) {
        int i = 0;       /* index in packed string  */
        int j = offset;  /* index in unpacked array */
        int l = packed.length();
        while (i < l) {
            int count = packed.charAt(i++);
            int value = packed.charAt(i++);
            do {
                result[j++] = value;
            } while (--count > 0);
        }
        return j;
    }
    /** the input device */
    private java.io.Reader zzReader;
    /** the current state of the DFA */
    private int zzState;
    /** the current lexical state */
    private int zzLexicalState = YYINITIAL;
    /** this buffer contains the current text to be matched and is
    the source of the yytext() string */
    private char zzBuffer[] = new char[ZZ_BUFFERSIZE];
    /** the textposition at the last accepting state */
    private int zzMarkedPos;
    /** the current text position in the buffer */
    private int zzCurrentPos;
    /** startRead marks the beginning of the yytext() string in the buffer */
    private int zzStartRead;
    /** endRead marks the last character in the buffer, that has been read
    from input */
    private int zzEndRead;
    /** number of newlines encountered up to the start of the matched text */
    private int yyline;
    /** the number of characters up to the start of the matched text */
    private int yychar;
    /**
     * the number of characters from the last newline up to the start of the
     * matched text
     */
    private int yycolumn;
    /**
     * zzAtBOL == true <=> the scanner is currently at the beginning of a line
     */
    private boolean zzAtBOL = true;
    /** zzAtEOF == true <=> the scanner is at the EOF */
    private boolean zzAtEOF;
    /** denotes if the user-EOF-code has already been executed */
    private boolean zzEOFDone;

    /* user code: */
//Authored by Leighton, Jack, Jon, Ian, Elena, Clement 
//Adapted from pltsnow project by Jon, Cesar, Vinay, Sharadh
    public Parser yyparser;

    /* Default constructor */
    public Yylex() {
    }

    /* constructor taking an additional parser object */
    public Yylex(java.io.Reader r, Parser yyparser) {
        this(r);
        this.yyparser = yyparser;
    }

    //Print to console
    private void p(String s) {
        //System.out.print(s + " ");
    }
    //Print line to console

    private void pl(String s) {
        //System.out.println(s);
    }

    private void l(int line, int col) {
        //System.out.print("("+line + "," + col + ")");
    }

    public int getLine() {
        return yyline;
    }

    /**
     * Creates a new scanner
     * There is also a java.io.InputStream version of this constructor.
     *
     * @param   in  the java.io.Reader to read input from.
     */
    Yylex(java.io.Reader in) {
        this.zzReader = in;
    }

    /**
     * Creates a new scanner.
     * There is also java.io.Reader version of this constructor.
     *
     * @param   in  the java.io.Inputstream to read input from.
     */
    Yylex(java.io.InputStream in) {
        this(new java.io.InputStreamReader(in));
    }

    /**
     * Unpacks the compressed character translation table.
     *
     * @param packed   the packed character translation table
     * @return         the unpacked character translation table
     */
    private static char[] zzUnpackCMap(String packed) {
        char[] map = new char[0x10000];
        int i = 0;  /* index in packed string  */
        int j = 0;  /* index in unpacked array */
        while (i < 152) {
            int count = packed.charAt(i++);
            char value = packed.charAt(i++);
            do {
                map[j++] = value;
            } while (--count > 0);
        }
        return map;
    }

    /**
     * Refills the input buffer.
     *
     * @return      <code>false</code>, iff there was new input.
     *
     * @exception   java.io.IOException  if any I/O-Error occurs
     */
    private boolean zzRefill() throws java.io.IOException {

        /* first: make room (if you can) */
        if (zzStartRead > 0) {
            System.arraycopy(zzBuffer, zzStartRead,
                    zzBuffer, 0,
                    zzEndRead - zzStartRead);

            /* translate stored positions */
            zzEndRead -= zzStartRead;
            zzCurrentPos -= zzStartRead;
            zzMarkedPos -= zzStartRead;
            zzStartRead = 0;
        }

        /* is the buffer big enough? */
        if (zzCurrentPos >= zzBuffer.length) {
            /* if not: blow it up */
            char newBuffer[] = new char[zzCurrentPos * 2];
            System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
            zzBuffer = newBuffer;
        }

        /* finally: fill the buffer with new input */
        int numRead = zzReader.read(zzBuffer, zzEndRead,
                zzBuffer.length - zzEndRead);

        if (numRead > 0) {
            zzEndRead += numRead;
            return false;
        }
        // unlikely but not impossible: read 0 characters, but not at end of stream
        if (numRead == 0) {
            int c = zzReader.read();
            if (c == -1) {
                return true;
            } else {
                zzBuffer[zzEndRead++] = (char) c;
                return false;
            }
        }

        // numRead < 0
        return true;
    }

    /**
     * Closes the input stream.
     */
    public final void yyclose() throws java.io.IOException {
        zzAtEOF = true;            /* indicate end of file */
        zzEndRead = zzStartRead;  /* invalidate buffer    */

        if (zzReader != null) {
            zzReader.close();
        }
    }

    /**
     * Resets the scanner to read from a new input stream.
     * Does not close the old reader.
     *
     * All internal variables are reset, the old input stream
     * <b>cannot</b> be reused (internal buffer is discarded and lost).
     * Lexical state is set to <tt>ZZ_INITIAL</tt>.
     *
     * @param reader   the new input stream
     */
    public final void yyreset(java.io.Reader reader) {
        zzReader = reader;
        zzAtBOL = true;
        zzAtEOF = false;
        zzEOFDone = false;
        zzEndRead = zzStartRead = 0;
        zzCurrentPos = zzMarkedPos = 0;
        yyline = yychar = yycolumn = 0;
        zzLexicalState = YYINITIAL;
    }

    /**
     * Returns the current lexical state.
     */
    public final int yystate() {
        return zzLexicalState;
    }

    /**
     * Enters a new lexical state
     *
     * @param newState the new lexical state
     */
    public final void yybegin(int newState) {
        zzLexicalState = newState;
    }

    /**
     * Returns the text matched by the current regular expression.
     */
    public final String yytext() {
        return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
    }

    /**
     * Returns the character at position <tt>pos</tt> from the
     * matched text.
     *
     * It is equivalent to yytext().charAt(pos), but faster
     *
     * @param pos the position of the character to fetch.
     *            A value from 0 to yylength()-1.
     *
     * @return the character at position pos
     */
    public final char yycharat(int pos) {
        return zzBuffer[zzStartRead + pos];
    }

    /**
     * Returns the length of the matched text region.
     */
    public final int yylength() {
        return zzMarkedPos - zzStartRead;
    }

    /**
     * Reports an error that occured while scanning.
     *
     * In a wellformed scanner (no or only correct usage of
     * yypushback(int) and a match-all fallback rule) this method
     * will only be called with things that "Can't Possibly Happen".
     * If this method is called, something is seriously wrong
     * (e.g. a JFlex bug producing a faulty scanner etc.).
     *
     * Usual syntax/scanner level error handling should be done
     * in error fallback rules.
     *
     * @param   errorCode  the code of the errormessage to display
     */
    private void zzScanError(int errorCode) {
        String message;
        try {
            message = ZZ_ERROR_MSG[errorCode];
        } catch (ArrayIndexOutOfBoundsException e) {
            message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
        }

        throw new Error(message);
    }

    /**
     * Pushes the specified amount of characters back into the input stream.
     *
     * They will be read again by then next call of the scanning method
     *
     * @param number  the number of characters to be read again.
     *                This number must not be greater than yylength()!
     */
    public void yypushback(int number) {
        if (number > yylength()) {
            zzScanError(ZZ_PUSHBACK_2BIG);
        }

        zzMarkedPos -= number;
    }

    /**
     * Contains user EOF-code, which will be executed exactly once,
     * when the end of file is reached
     */
    private void zzDoEOF() throws java.io.IOException {
        if (!zzEOFDone) {
            zzEOFDone = true;
            yyclose();
        }
    }

    /**
     * Resumes scanning until the next regular expression is matched,
     * the end of input is encountered or an I/O-Error occurs.
     *
     * @return      the next token
     * @exception   java.io.IOException  if any I/O-Error occurs
     */
    public int yylex() throws java.io.IOException {
        int zzInput;
        int zzAction;

        // cached fields:
        int zzCurrentPosL;
        int zzMarkedPosL;
        int zzEndReadL = zzEndRead;
        char[] zzBufferL = zzBuffer;
        char[] zzCMapL = ZZ_CMAP;

        int[] zzTransL = ZZ_TRANS;
        int[] zzRowMapL = ZZ_ROWMAP;
        int[] zzAttrL = ZZ_ATTRIBUTE;

        while (true) {
            zzMarkedPosL = zzMarkedPos;

            boolean zzR = false;
            for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                    zzCurrentPosL++) {
                switch (zzBufferL[zzCurrentPosL]) {
                    case '\u000B':
                    case '\u000C':
                    case '\u0085':
                    case '\u2028':
                    case '\u2029':
                        yyline++;
                        yycolumn = 0;
                        zzR = false;
                        break;
                    case '\r':
                        yyline++;
                        yycolumn = 0;
                        zzR = true;
                        break;
                    case '\n':
                        if (zzR) {
                            zzR = false;
                        } else {
                            yyline++;
                            yycolumn = 0;
                        }
                        break;
                    default:
                        zzR = false;
                        yycolumn++;
                }
            }

            if (zzR) {
                // peek one character ahead if it is \n (if we have counted one line too much)
                boolean zzPeek;
                if (zzMarkedPosL < zzEndReadL) {
                    zzPeek = zzBufferL[zzMarkedPosL] == '\n';
                } else if (zzAtEOF) {
                    zzPeek = false;
                } else {
                    boolean eof = zzRefill();
                    zzEndReadL = zzEndRead;
                    zzMarkedPosL = zzMarkedPos;
                    zzBufferL = zzBuffer;
                    if (eof) {
                        zzPeek = false;
                    } else {
                        zzPeek = zzBufferL[zzMarkedPosL] == '\n';
                    }
                }
                if (zzPeek) {
                    yyline--;
                }
            }
            zzAction = -1;

            zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

            zzState = ZZ_LEXSTATE[zzLexicalState];


            zzForAction:
            {
                while (true) {

                    if (zzCurrentPosL < zzEndReadL) {
                        zzInput = zzBufferL[zzCurrentPosL++];
                    } else if (zzAtEOF) {
                        zzInput = YYEOF;
                        break zzForAction;
                    } else {
                        // store back cached positions
                        zzCurrentPos = zzCurrentPosL;
                        zzMarkedPos = zzMarkedPosL;
                        boolean eof = zzRefill();
                        // get translated positions and possibly new buffer
                        zzCurrentPosL = zzCurrentPos;
                        zzMarkedPosL = zzMarkedPos;
                        zzBufferL = zzBuffer;
                        zzEndReadL = zzEndRead;
                        if (eof) {
                            zzInput = YYEOF;
                            break zzForAction;
                        } else {
                            zzInput = zzBufferL[zzCurrentPosL++];
                        }
                    }
                    int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
                    if (zzNext == -1) {
                        break zzForAction;
                    }
                    zzState = zzNext;

                    int zzAttributes = zzAttrL[zzState];
                    if ((zzAttributes & 1) == 1) {
                        zzAction = zzState;
                        zzMarkedPosL = zzCurrentPosL;
                        if ((zzAttributes & 8) == 8) {
                            break zzForAction;
                        }
                    }

                }
            }

            // store back cached position
            zzMarkedPos = zzMarkedPosL;

            switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
                case 27: {
                    l(yyline + 1, yycolumn);
                    p(yytext());
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    yyparser.yylval.obj = "string";
                    return yyparser.QUOTE;
                }
                case 52:
                    break;
                case 37: {
                    p("ARRAY");
                    return yyparser.ARRAY;
                }
                case 53:
                    break;
                case 9: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.MINUS;
                }
                case 54:
                    break;
                case 36: {
                    p("HASH");
                    return yyparser.HASH;
                }
                case 55:
                    break;
                case 3: {
                    pl("");
                }
                case 56:
                    break;
                case 10: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.MOD;
                }
                case 57:
                    break;
                case 12: {
                    p("=");
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.EQUALS;
                }
                case 58:
                    break;
                case 28: {
                    l(yyline, yycolumn);
                    p("COMMENT");
                    return yyparser.COMMENT;
                }
                case 59:
                    break;
                case 18: {
                    p("COLON");
                    return yyparser.COLON;
                }
                case 60:
                    break;
                case 50: {
                    p("RETURNS");
                    return yyparser.RETURNS;
                }
                case 61:
                    break;
                case 49: {
                    p("ACCEPTS");
                    return yyparser.ACCEPTS;
                }
                case 62:
                    break;
                case 25: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.REL_OP_GE;
                }
                case 63:
                    break;
                case 15: {
                    p("RPAREN");
                    return yyparser.RPAREN;
                }
                case 64:
                    break;
                case 5: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.DIV;
                }
                case 65:
                    break;
                case 7: {
                    l(yyline + 1, yycolumn);
                    p(yytext());
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.IDENTIFIER;
                }
                case 66:
                    break;
                case 31: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.LOG_OP_NOT;
                }
                case 67:
                    break;
                case 8: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.PLUS;
                }
                case 68:
                    break;
                case 43: {
                    p("NUMBER");
                    return yyparser.NUMBER;
                }
                case 69:
                    break;
                case 40: {
                    p("WHILE");
                    return yyparser.WHILE;
                }
                case 70:
                    break;
                case 13: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.REL_OP_GT;
                }
                case 71:
                    break;
                case 16: {
                    p("LEFT_SQUARE_PAREN");
                    return yyparser.LEFT_SQUARE_PAREN;
                }
                case 72:
                    break;
                case 23: {
                    p("IF");
                    return yyparser.IF;
                }
                case 73:
                    break;
                case 30: {
                    p("AND");
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.LOG_OP_AND;
                }
                case 74:
                    break;
                case 41: {
                    pl("BEGIN");
                    return yyparser.BEGIN;
                }
                case 75:
                    break;
                case 20: {
                    p("COMMA");
                    return yyparser.COMMA;
                }
                case 76:
                    break;
                case 45: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.LOG_OP_EQUAL;
                }
                case 77:
                    break;
                case 17: {
                    p("RIGHT_SQUARE_PAREN");
                    return yyparser.RIGHT_SQUARE_PAREN;
                }
                case 78:
                    break;
                case 39: {
                    p("FALSE");
                    yyparser.yylval = new ParserVal("false", yyline + 1, yycolumn);
                    yyparser.yylval.obj = "boolean";
                    return yyparser.FALSE;
                }
                case 79:
                    break;
                case 29: {
                    p("FOR");
                    return yyparser.FOR;
                }
                case 80:
                    break;
                case 38: {
                    p("PRINT");
                    return yyparser.PRINT;
                }
                case 81:
                    break;
                case 22: {
                    p("IN");
                    return yyparser.IN;
                }
                case 82:
                    break;
                case 21: {
                    p("DOT");
                    return yyparser.DOT;
                }
                case 83:
                    break;
                case 24: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.REL_OP_LE;
                }
                case 84:
                    break;
                case 44: {
                    p("RETURN");
                    return yyparser.RETURN;
                }
                case 85:
                    break;
                case 2: {
                    p("WHO KNOWS");
                    return 0;
                }
                case 86:
                    break;
                case 26: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.LOG_OP_OR;
                }
                case 87:
                    break;
                case 42: {
                    p("STRING");
                    return yyparser.STRING;
                }
                case 88:
                    break;
                case 33: {
                    p("EACH");
                    return yyparser.EACH;
                }
                case 89:
                    break;
                case 11: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.REL_OP_LT;
                }
                case 90:
                    break;
                case 32: {
                    pl("END");
                    return yyparser.END;
                }
                case 91:
                    break;
                case 6: {
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    return yyparser.MUL;
                }
                case 92:
                    break;
                case 48: {
                    p("NOTHING");
                    return yyparser.NOTHING;
                }
                case 93:
                    break;
                case 46: {
                    p("ELSEIF");
                    return yyparser.ELSEIF;
                }
                case 94:
                    break;
                case 34: {
                    p("ELSE");
                    return yyparser.ELSE;
                }
                case 95:
                    break;
                case 35: {
                    p("TRUE");
                    yyparser.yylval = new ParserVal("true", yyline + 1, yycolumn);
                    yyparser.yylval.obj = "boolean";
                    return yyparser.TRUE;
                }
                case 96:
                    break;
                case 14: {
                    p("LPAREN");
                    return yyparser.LPAREN;
                }
                case 97:
                    break;
                case 47: {
                    p("BOOLEAN");
                    return yyparser.BOOLEAN;
                }
                case 98:
                    break;
                case 19: {
                    pl("SEMICOLON");
                    return yyparser.SEMICOLON;
                }
                case 99:
                    break;
                case 51: {
                    p("FUNCTION");
                    return yyparser.FUNCTION;
                }
                case 100:
                    break;
                case 1: {
                    l(yyline + 1, yycolumn);
                    p(yytext());
                    yyparser.yylval = new ParserVal(yytext(), yyline + 1, yycolumn);
                    yyparser.yylval.obj = "number";
                    return yyparser.NUMERIC;
                }
                case 101:
                    break;
                case 4: {
                }
                case 102:
                    break;
                default:
                    if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
                        zzAtEOF = true;
                        zzDoEOF();
                        {
                            return 0;
                        }
                    } else {
                        zzScanError(ZZ_NO_MATCH);
                    }
            }
        }
    }
}

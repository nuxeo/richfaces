package org.richfaces.seamparser;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import junit.framework.TestCase;
import org.jboss.seam.text.SeamTextLexer;
import org.jboss.seam.text.SeamTextParser;
import org.richfaces.convert.seamtext.HtmlToSeamSAXParser;

import java.io.StringReader;

/**
 * @user: akolonitsky Date: Mar 25, 2009
 */
public class HtmlSeamParserTest extends TestCase {
    
    private final static String SEAM_TEXT_EXPRESSION_1 = "It's easy to make *emphasis* -1- *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_.";
    
    private final static String SEAM_TEXT_EXPRESSION_2 = "+ This is a big heading\n"
            + "You /must/ have some text following a heading!\n\n"
            + "++ This is a smaller heading\n"
            + "This is the first paragraph. We can split it across multiple"
            + "lines, but we must end it with a blank line.\n\n"
            + "This is the second paragraph.";
    
    private final static String SEAM_TEXT_EXPRESSION_3 = "An ordered list:\n\n"
            + "# first item\n" + "# second item\n"
            + "# and even the /third/ item\n\n" + "An unordered list:\n\n"
            + "= an item\n" + "= another item";
    
    private final static String SEAM_TEXT_EXPRESSION_4 = "The other guy said: "
            + "\"Nyeah nyeah-nee\"";
    
    private final static String SEAM_TEXT_EXPRESSION_5 = "You can write down equations like 2\\*3\\+4-7\\=3 and HTML tagslike \\<body\\> using the escape character: \\\\. foo@tut.by, 100$ cash 100%";
    
    private final static String SEAM_TEXT_EXPRESSION_6 = "My code doesn't work:"
            + "`for (int i=0; i<100; i--)\n"
            + "{\n"
            + "doSomething(){ String str = \"&amp;&nbsp;&gt;&quot; \"; }; doSomething();\n"
            + "doSomething() " + "}`" + " Any ideas?";
    
    private final static String SEAM_TEXT_EXPRESSION_7 = "+ test value<h1>test1<h2>test2</h2>test4</h1>\ntest";
    
    private final static String SEAM_TEXT_EXPRESSION_8 = "++ test value<h1>test1<h2>test2</h2>test4</h1>\ntest";
    
    private final static String SEAM_TEXT_EXPRESSION_9 = "+++ test value<h1>test1<h2>test2</h2>test4</h1>\ntest";
    
    private final static String SEAM_TEXT_EXPRESSION_10 = "++++ test value<h1>test1<h2>test2</h2>test4</h1>\ntest";
    
    private final static String SEAM_TEXT_EXPRESSION_11 = "+ test value<div>test5</div><h1>test1<div>test2</div>test4</h1>\ntest";
    
    private final static String SEAM_TEXT_EXPRESSION_12 = "[test link=>http://test.com]";
    
    private final static String SEAM_TEXT_EXPRESSION_13 = "[=>http://test.com]";
    
    private final static String SEAM_TEXT_EXPRESSION_14 = "This is a |<tag attribute=\"value\"/>| example.";
    
    private final static String SEAM_TEXT_EXPRESSION_15 = "= <div class=\"testClass1 testClass2\"></div><h1> test value </h1>";
    
    private final static String SEAM_TEXT_EXPRESSION_16 = "# <div class=\"testClass1 testClass2\"></div><h1> test value </h1>";
    
    private final static String SEAM_TEXT_EXPRESSION_17 = "paragraph\n\n+ header\ntext after header\n\nanother paragraph";
    
    private final static String SEAM_TEXT_EXPRESSION_18 = "paragraph\n\n++ header\ntext after header\n\nanother paragraph";
    
    private final static String SEAM_TEXT_EXPRESSION_19 = "paragraph\n\n+++ header\ntext after header\n\nanother paragraph";
    
    private final static String SEAM_TEXT_EXPRESSION_20 = "paragraph\n\n++++ header\ntext after header\n\nanother paragraph";
    
    private final static String SEAM_TEXT_EXPRESSION_21 = "paragraph\n\n= item1\n= item2\n= item3\n\nanother paragraph";
    
    private final static String SEAM_TEXT_EXPRESSION_22 = "paragraph\n\n# item1\n# item2\n# item3\n\nanother paragraph";
    
    private final static String SEAM_TEXT_EXPRESSION_23 = "+ header text *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_\n text after header *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_";
    
    private final static String SEAM_TEXT_EXPRESSION_24 = "++ header text *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_\n text after header *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_";
    
    private final static String SEAM_TEXT_EXPRESSION_25 = "+++ header text *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_\n text after header *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_";
    
    private final static String SEAM_TEXT_EXPRESSION_26 = "++++ header text *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_\n text after header *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_";
    
    private final static String SEAM_TEXT_EXPRESSION_27 = "= item1 *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_\n= item2 *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_";
    
    private final static String SEAM_TEXT_EXPRESSION_28 = "# item1 *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_\n# item2 *emphasis*, |monospace|, "
            + "~deleted text~, super^scripts^ or_underlines_";
    
    private final static String SEAM_TEXT_EXPRESSION_29 = "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9";
    
    public HtmlSeamParserTest(String name) {
        super(name);
    }
    
    public void testSeamTextConverting1() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_1);
    }
    
    public void testStandartSeamTextConverting2() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_2);
    }
    
    public void testStandartSeamTextConverting3() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_3);
    }
    
    public void testStandartSeamTextConverting4() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_4);
    }
    
    public void testStandartSeamTextConverting5() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_5);
    }
    
    /* 
     * Element <pre> not processed after https://jira.jboss.org/jira/browse/RF-7030
     * */ 
    public void standartSeamTextConverting6() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_6);
    }
    
    public void testStandartSeamTextConverting7() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_7);
    }
    
    public void testStandartSeamTextConverting8() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_8);
    }
    
    public void testStandartSeamTextConverting9() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_9);
    }
    
    public void testStandartSeamTextConverting10() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_10);
    }
    
    public void testStandartSeamTextConverting11() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_11);
    }
    
    public void testStandartSeamTextConverting12() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_12);
    }
    
    public void testStandartSeamTextConverting13() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_13);
    }
    
    public void testStandartSeamTextConverting14() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_14);
    }
    
    public void testStandartSeamTextConverting15() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_15);
    }
    
    public void testStandartSeamTextConverting16() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_16);
    }
    
    public void testStandartSeamTextConverting17() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_17);
    }
    
    public void testStandartSeamTextConverting18() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_18);
    }
    
    public void testStandartSeamTextConverting19() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_19);
    }
    
    public void testStandartSeamTextConverting20() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_20);
    }
    
    public void testStandartSeamTextConverting21() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_21);
    }
    
    public void testStandartSeamTextConverting22() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_22);
    }
    
    public void testStandartSeamTextConverting23() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_23);
    }
    
    public void testStandartSeamTextConverting24() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_24);
    }
    
    public void testStandartSeamTextConverting25() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_25);
    }
    
    public void testStandartSeamTextConverting26() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_26);
    }
    
    public void testStandartSeamTextConverting27() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_27);
    }
    
    public void testStandartSeamTextConverting28() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_28);
    }
    
    public void testStandartSeamTextConverting29() throws Exception {
        assertSeamConverting(SEAM_TEXT_EXPRESSION_29);
    }
    
    /* 
     * Element <pre> not processed after https://jira.jboss.org/jira/browse/RF-7030
     * */ 
    public void standartSeamTextConverting30() throws Exception {
        assertSeamConverting("`for (int i=0; i<100; i--)\n{\n    doSomething();\n}`");
    }
    
    public void testRF5717() throws Exception {
        assertHtml2SeamConverting("<p>a&lt;b a&amp;b</p>");
    }
    
    public void testRF6725() throws Exception {
        assertHtml2SeamConverting("&radic; &euro; &cent;");
    }
    
    public void testNestingFormating() throws Exception {
        assertHtml2SeamConverting("<p><b>aaaaaaaaa <u><i class=\"seamTextEmphasis\">sssssssss</i> dddddddddddddddd</u></b></p>");
    }
    
    public void testNestingFormating1() throws Exception {
        assertHtml2SeamConverting("<P><STRONG>aaaaaaaaad <U><B>ddddddddddddd</B> sssssssssssss</U></STRONG></P>");
    }
    
    public void testSkipComment() throws Exception {
        assertHtml2SeamConverting("<!-- Hello Cfif -->");
    }
    
    public void testEmptyList() throws Exception {
        assertHtml2SeamConverting("<ul><li/></ul><ol><li/></ol><ul />");
    }
    
    public void testEmptyList02() throws Exception {
        assertHtml2SeamConverting("<ul><li><ul><li /></ul></li></ul>");
    }
    
    public void testEmrtyTags() throws Exception {
        final String html = "<br/>";
        final String result = assertHtml2SeamConverting(html);
        
        assertEquals(html, result);
    }
    
    public void testEmrtyTags02() throws Exception {
        assertHtml2SeamConverting(" <br/>Hello <hr/><img/><area/> World! <col/>");
    }
    
    public void testPreformated() throws Exception {
        final String html = "<pre>aaaaa<br/>bbbb<br/></pre>";
        final String result = assertHtml2SeamConverting(html);
        
        assertTrue(result.contains(html));
    }
    
    public void testPreformated02() throws Exception {
        final String html = "<pre><br/><sup>sdsdf</sup></pre>";
        final String result = assertHtml2SeamConverting(html);
        
        assertTrue(result.contains(html));
    }
    
    public void testPreformated03() throws Exception {
        final String html = "<pre>&lt;&gt;</pre>";
        final String result = assertHtml2SeamConverting(html);
        
        assertTrue(result.equals(html));
    }
    
    public void testHeading() throws Exception {
        assertHtml2SeamConverting("<h1></h1><p>\nqwe<br/></p>\n\n\n<h2>adsfasdf</h2>qwe");
    }
    
    public void testHeading02() throws Exception {
        assertHtml2SeamConverting("<h1></h1>");
    }
    
    public void testHeading03() throws Exception {
        assertHtml2SeamConverting("<h1></h1>qwe\n<h1></h1><h1></h1>");
    }
    
    public void testHeading04() throws Exception {
        assertHtml2SeamConverting("<h1></h1>qwe");
    }
    
    public void testList() throws Exception {
        assertHtml2SeamConverting("<ol>       \n\n<li>first item</li>\n\n<li>second item</li>\n\n<li>and even the <i>third</i> item</li>\n\n</ol>");
    }
    
    public void testFullList() throws Exception {
        assertHtml2SeamConverting("<p>\n\nAn ordered list:\n\n</p>\n\n \n\n<ol>       \n\n<li>first item</li>\n\n<li>second item</li>\n\n<li>and even the <i>third</i> item</li>\n\n</ol>\n\n\n\n<p>\n\nAn unordered list:\n\n</p>\n\n\n\n<ul>\n\n<li>an item</li>\n\n<li>another item</li>\n\n</ul>\n");
    }
    
    public void testBlockquote() throws Exception {
        assertHtml2SeamConverting("<p>\n\nThe other guy said:\n\n</p>\n\n        \n\n<q>Nyeah nyeah-nee\n<i>nyeah</i> nyeah!</q>\n\n\n\n<p>\n\nBut what do you think he means by <q>nyeah-nee</q>?\n\n</p>");
    }
    
    public void testBlockquote02() throws Exception {
        assertHtml2SeamConverting("asdsadasd <q>word</q>  my <span>red</span> ");
    }
    
    public void testText() throws Exception {
        assertHtml2SeamConverting("test    ");
    }
    
    public void testSeamDocumentation01() throws Exception {
        assertHtml2SeamConverting("<p>\nIt's easy to make <i>emphasis</i>, <tt>monospace</tt>\n<del>deleted text</del>, super<sup>scripts</sup> or <u>underlines</u>.\n</p>");
    }
    
    public void testSeamDocumentation02() throws Exception {
        final String content = "\nYou can write down equations like 2*3=6 and HTML tags\nlike &lt;body&gt; using the escape character: \\.\n";
        final String result = assertHtml2SeamConverting("<p>"+content+"</p>");
        
        assertTrue(result.contains(content));
    }
    
    public void testUglyTextFromWord() throws Exception {
        final String str = "<p><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/><meta content=\"Word.Document\" name=\"ProgId\"/><meta content=\"Microsoft Word 12\" name=\"Generator\"/><meta content=\"Microsoft Word 12\" name=\"Originator\"/><link href=\"file:///E:\\TEMP~1\\msohtmlclip1\\01\\clip_filelist.xml\" rel=\"File-List\"/><link href=\"file:///E:\\TEMP~1\\msohtmlclip1\\01\\clip_themedata.thmx\" rel=\"themeData\"/><link href=\"file:///E:\\TEMP~1\\msohtmlclip1\\01\\clip_colorschememapping.xml\" rel=\"colorSchemeMapping\"/>"
                + "<!--[if gte mso 9]><xml>\n"
                + " <w:WordDocument>\n"
                + "  <w:View>Normal</w:View>\n"
                + "  <w:Zoom>0</w:Zoom>\n"
                + "  <w:TrackMoves/>\n"
                + "  <w:TrackFormatting/>\n"
                + "  <w:PunctuationKerning/>\n"
                + "  <w:ValidateAgainstSchemas/>\n"
                + "  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>\n"
                + "  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>\n"
                + "  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>\n"
                + "  <w:DoNotPromoteQF/>\n"
                + "  <w:LidThemeOther>EN-US</w:LidThemeOther>\n"
                + "  <w:LidThemeAsian>X-NONE</w:LidThemeAsian>\n"
                + "  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>\n"
                + "  <w:Compatibility>\n"
                + "   <w:BreakWrappedTables/>\n"
                + "   <w:SnapToGridInCell/>\n"
                + "   <w:WrapTextWithPunct/>\n"
                + "   <w:UseAsianBreakRules/>\n"
                + "   <w:DontGrowAutofit/>\n"
                + "   <w:SplitPgBreakAndParaMark/>\n"
                + "   <w:DontVertAlignCellWithSp/>\n"
                + "   <w:DontBreakConstrainedForcedTables/>\n"
                + "   <w:DontVertAlignInTxbx/>\n"
                + "   <w:Word11KerningPairs/>\n"
                + "   <w:CachedColBalance/>\n"
                + "  </w:Compatibility>\n"
                + "  <w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>\n"
                + "  <m:mathPr>\n"
                + "   <m:mathFont m:val=\"Cambria Math\"/>\n"
                + "   <m:brkBin m:val=\"before\"/>\n"
                + "   <m:brkBinSub m:val=\"-\"/>\n"
                + "   <m:smallFrac m:val=\"off\"/>\n"
                + "   <m:dispDef/>\n"
                + "   <m:lMargin m:val=\"0\"/>\n"
                + "   <m:rMargin m:val=\"0\"/>\n"
                + "   <m:defJc m:val=\"centerGroup\"/>\n"
                + "   <m:wrapIndent m:val=\"1440\"/>\n"
                + "   <m:intLim m:val=\"subSup\"/>\n"
                + "   <m:naryLim m:val=\"undOvr\"/>\n"
                + "  </m:mathPr></w:WordDocument>\n"
                + "</xml><![endif]-->"
                + "<!--[if gte mso 9]><xml>\n"
                + " <w:LatentStyles DefLockedState=\"false\" DefUnhideWhenUsed=\"true\"\n"
                + "  DefSemiHidden=\"true\" DefQFormat=\"false\" DefPriority=\"99\"\n"
                + "  LatentStyleCount=\"267\">\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"0\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Normal\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"heading 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 7\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 8\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"9\" QFormat=\"true\" Name=\"heading 9\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 7\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 8\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" Name=\"toc 9\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"35\" QFormat=\"true\" Name=\"caption\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"10\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Title\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"1\" Name=\"Default Paragraph Font\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"11\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Subtitle\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"22\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Strong\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"20\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Emphasis\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"59\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Table Grid\"/>\n"
                + "  <w:LsdException Locked=\"false\" UnhideWhenUsed=\"false\" Name=\"Placeholder Text\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"1\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"No Spacing\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Shading\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light List\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Grid\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Dark List\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful List\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" UnhideWhenUsed=\"false\" Name=\"Revision\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"34\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"List Paragraph\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"29\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Quote\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"30\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Intense Quote\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 1\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 2\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 3\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 4\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 5\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"60\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Shading Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"61\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light List Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"62\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Light Grid Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"63\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 1 Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"64\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Shading 2 Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"65\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 1 Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"66\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium List 2 Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"67\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 1 Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"68\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 2 Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"69\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Medium Grid 3 Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"70\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Dark List Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"71\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Shading Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"72\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful List Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"73\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" Name=\"Colorful Grid Accent 6\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"19\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Subtle Emphasis\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"21\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Intense Emphasis\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"31\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Subtle Reference\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"32\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Intense Reference\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"33\" SemiHidden=\"false\"\n"
                + "   UnhideWhenUsed=\"false\" QFormat=\"true\" Name=\"Book Title\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"37\" Name=\"Bibliography\"/>\n"
                + "  <w:LsdException Locked=\"false\" Priority=\"39\" QFormat=\"true\" Name=\"TOC Heading\"/>\n"
                + " </w:LatentStyles>\n"
                + "</xml><![endif]"
                + "-->"
                + "<style>\n"
                + "<!--\n"
                + " /* Font Definitions */\n"
                + " @font-face\n"
                + "\t{font-family:\"Cambria Math\";\n"
                + "\tpanose-1:2 4 5 3 5 4 6 3 2 4;\n"
                + "\tmso-font-charset:204;\n"
                + "\tmso-generic-font-family:roman;\n"
                + "\tmso-font-pitch:variable;\n"
                + "\tmso-font-signature:-1610611985 1107304683 0 0 159 0;}\n"
                + "@font-face\n"
                + "\t{font-family:Calibri;\n"
                + "\tpanose-1:2 15 5 2 2 2 4 3 2 4;\n"
                + "\tmso-font-charset:204;\n"
                + "\tmso-generic-font-family:swiss;\n"
                + "\tmso-font-pitch:variable;\n"
                + "\tmso-font-signature:-1610611985 1073750139 0 0 159 0;}\n"
                + " /* Style Definitions */\n"
                + " p.MsoNormal, li.MsoNormal, div.MsoNormal\n"
                + "\t{mso-style-unhide:no;\n"
                + "\tmso-style-qformat:yes;\n"
                + "\tmso-style-parent:\"\";\n"
                + "\tmargin-top:0in;\n"
                + "\tmargin-right:0in;\n"
                + "\tmargin-bottom:10.0pt;\n"
                + "\tmargin-left:0in;\n"
                + "\tline-height:115%;\n"
                + "\tmso-pagination:widow-orphan;\n"
                + "\tfont-size:11.0pt;\n"
                + "\tfont-family:\"Calibri\",\"sans-serif\";\n"
                + "\tmso-ascii-font-family:Calibri;\n"
                + "\tmso-ascii-theme-font:minor-latin;\n"
                + "\tmso-fareast-font-family:Calibri;\n"
                + "\tmso-fareast-theme-font:minor-latin;\n"
                + "\tmso-hansi-font-family:Calibri;\n"
                + "\tmso-hansi-theme-font:minor-latin;\n"
                + "\tmso-bidi-font-family:\"Times New Roman\";\n"
                + "\tmso-bidi-theme-font:minor-bidi;}\n"
                + "a:link, span.MsoHyperlink\n"
                + "\t{mso-style-noshow:yes;\n"
                + "\tmso-style-priority:99;\n"
                + "\tcolor:blue;\n"
                + "\ttext-decoration:underline;\n"
                + "\ttext-underline:single;}\n"
                + "a:visited, span.MsoHyperlinkFollowed\n"
                + "\t{mso-style-noshow:yes;\n"
                + "\tmso-style-priority:99;\n"
                + "\tcolor:purple;\n"
                + "\tmso-themecolor:followedhyperlink;\n"
                + "\ttext-decoration:underline;\n"
                + "\ttext-underline:single;}\n"
                + "p\n"
                + "\t{mso-style-noshow:yes;\n"
                + "\tmso-style-priority:99;\n"
                + "\tmso-margin-top-alt:auto;\n"
                + "\tmargin-right:0in;\n"
                + "\tmargin-bottom:5.75pt;\n"
                + "\tmargin-left:0in;\n"
                + "\tmso-pagination:widow-orphan;\n"
                + "\tfont-size:12.0pt;\n"
                + "\tfont-family:\"Times New Roman\",\"serif\";\n"
                + "\tmso-fareast-font-family:\"Times New Roman\";}\n"
                + ".MsoChpDefault\n"
                + "\t{mso-style-type:export-only;\n"
                + "\tmso-default-props:yes;\n"
                + "\tmso-ascii-font-family:Calibri;\n"
                + "\tmso-ascii-theme-font:minor-latin;\n"
                + "\tmso-fareast-font-family:Calibri;\n"
                + "\tmso-fareast-theme-font:minor-latin;\n"
                + "\tmso-hansi-font-family:Calibri;\n"
                + "\tmso-hansi-theme-font:minor-latin;\n"
                + "\tmso-bidi-font-family:\"Times New Roman\";\n"
                + "\tmso-bidi-theme-font:minor-bidi;}\n"
                + ".MsoPapDefault\n"
                + "\t{mso-style-type:export-only;\n"
                + "\tmargin-bottom:10.0pt;\n"
                + "\tline-height:115%;}\n"
                + "@page Section1\n"
                + "\t{size:595.3pt 841.9pt;\n"
                + "\tmargin:56.7pt 42.5pt 56.7pt 85.05pt;\n"
                + "\tmso-header-margin:.5in;\n"
                + "\tmso-footer-margin:.5in;\n"
                + "\tmso-paper-source:0;}\n"
                + "div.Section1\n"
                + "\t{page:Section1;}\n"
                + "-->\n"
                + "</style>"
                + "<!--[if gte mso 10]>\n"
                + "<style>\n"
                + " /* Style Definitions */\n"
                + " table.MsoNormalTable\n"
                + "\t{mso-style-name:\"?z?\u00b1N\u2039N\u2021???\u00b0N? N\u201a?\u00b0?\u00b1?\u00bb??N\u2020?\u00b0\";\n"
                + "\tmso-tstyle-rowband-size:0;\n"
                + "\tmso-tstyle-colband-size:0;\n"
                + "\tmso-style-noshow:yes;\n"
                + "\tmso-style-priority:99;\n"
                + "\tmso-style-qformat:yes;\n"
                + "\tmso-style-parent:\"\";\n"
                + "\tmso-padding-alt:0in 5.4pt 0in 5.4pt;\n"
                + "\tmso-para-margin-top:0in;\n"
                + "\tmso-para-margin-right:0in;\n"
                + "\tmso-para-margin-bottom:10.0pt;\n"
                + "\tmso-para-margin-left:0in;\n"
                + "\tline-height:115%;\n"
                + "\tmso-pagination:widow-orphan;\n"
                + "\tfont-size:11.0pt;\n"
                + "\tfont-family:\"Calibri\",\"sans-serif\";\n"
                + "\tmso-ascii-font-family:Calibri;\n"
                + "\tmso-ascii-theme-font:minor-latin;\n"
                + "\tmso-fareast-font-family:\"Times New Roman\";\n"
                + "\tmso-fareast-theme-font:minor-fareast;\n"
                + "\tmso-hansi-font-family:Calibri;\n"
                + "\tmso-hansi-theme-font:minor-latin;}\n"
                + "</style>\n"
                + "<![endif]"
                + "-->\n"
                + "\n"
                + "<p align=\"center\" style=\"margin-bottom: 0.0001pt; text-align: center;\"><a name=\"OLE_LINK2\"/><a name=\"OLE_LINK1\"><span style=\"\"><b><span style='font-size: 20pt; font-family: \"Courier New\"; color: black;'>We where\n"
                + "unsuccessful in reproducting this bug in our testing</span></b></span></a></p>\n"
                + "\n"
                + "<p align=\"center\" style=\"margin-bottom: 0.0001pt; text-align: center;\"><span style=\"\"><span style=\"\"><b><span style='font-size: 20pt; font-family: \"Courier New\"; color: black;'>environment.</span></b></span></span></p>\n"
                + "\n"
                + "<p style=\"margin-bottom: 0.0001pt;\"><span style=\"\"><span style=\"\"><o:p>A\u00a0</o:p></span></span></p>\n"
                + "\n"
                + "<p style=\"margin-bottom: 0.0001pt;\"><span style=\"\"><span style=\"\"><s><span style='font-size: 13pt; font-family: \"Courier New\"; color: black;'>Could you provide us with a example URL\n"
                + "where this is happening?</span></s></span></span></p>\n"
                + "\n"
                + "<p style=\"margin-bottom: 0.0001pt;\"><span style=\"\"><span style=\"\"><span style='font-size: 13pt; font-family: \"Courier New\"; color: black;'>Do you have any odd browser\n"
                + "extensions/plugins installed?</span></span></span></p>\n"
                + "\n"
                + "<p style=\"margin-bottom: 0.0001pt;\"><span style=\"\"><span style=\"\"><span style='font-size: 13pt; font-family: \"Courier New\"; color: black;'>Does it happen on our site aswell </span></span></span><a href=\"http://tinymce.moxiecode.com/\"><span style=\"\"><span style=\"\"><span style='font-size: 13pt; font-family: \"Courier New\";'>http://tinymce.moxiecode.com</span></span></span></a><span style=\"\"><span style=\"\"><span style='font-size: 13pt; font-family: \"Courier New\"; color: black;'>?</span></span></span></p>\n"
                + "\n"
                + "<p style=\"margin-bottom: 0.0001pt;\"><span style=\"\"><span style=\"\"><o:p>A\u00a0</o:p></span></span></p>\n"
                + "\n"
                + "<span style=\"\"/><span style=\"\"/>\n"
                + "\n"
                + "<p class=\"MsoNormal\"><o:p>A\u00a0</o:p></p>\n"
                + "\n"
                + "</p>";
        assertHtml2SeamConverting(str);
    }
    
    public void testTextFromOpenOffice() throws Exception {
        assertHtml2SeamConverting("<p style=\"margin-bottom: 0in; line-height: 100%;\" >"
                + "  <meta http-equiv=\"CONTENT-TYPE\" content=\"text/html;\" charset=\"utf-8\" />"
                + "  <title></title>"
                + "  <meta name=\"GENERATOR\" content=\"OpenOffice.org 3.0  (Win32)\" />"
                + "  <style type=\"text/css\"><!--"
                + "    &lt;!"
                + "        @page { margin: 0.79in }"
                + "        P { margin-bottom: 0.08in }"
                + "     &gt;"
                + "  --></style>"
                + "</p>"
                + "<p style=\"margin-bottom: 0in; line-height: 100%;\" "
                + "   align=\"center\" "
                + "   lang=\"en-US\">"
                + "  <font color=\"#000000\">"
                + "     <font color=\"#000001\">"
                + "       <font color=\"#000002\" style=\"font-size: 20pt;\" size=\"5\">"
                + "         <b>We where unsuccessful in reproducting this bug in our testing</b>"
                + "       </font>"
                + "     </font>"
                + "  </font>"
                + "</p>"
                + "<p style=\"margin-bottom: 0in; line-height: 100%;\" "
                + "   align=\"center\" "
                + "   lang=\"en-US\">"
                + "  <font color=\"#000000\">"
                + "    <font>"
                + "      <font style=\"font-size: 20pt;\" size=\"5\">"
                + "        <b>environment.</b>"
                + "      </font>"
                + "    </font>" + "  </font>" + "</p>");
    }
    
    private String assertHtml2SeamConverting(String htmlText) throws Exception {
        System.out.println("Html -> SeamText -> Html");
        System.out.println("------------------- Html -----------------------");
        System.out.println("html = " + htmlText);
        
        
        System.out.println("------------------- SeamText -------------------");
        final String seamText = convertHtmlToSeamText(htmlText);
        System.out.println("seamText = \n'" + seamText + "'");
        
        final SeamTextParser seamParser = 
            new SeamTextParser(new SeamTextLexer(new StringReader(seamText)));
        seamParser.startRule();
        
        final String string = seamParser.toString();
        System.out.println("------------------- Html -----------------------");
        System.out.println("html = " + string);
        
        return string;
    }
    
    private void assertSeamConverting(String seamTextExpression)
            throws TokenStreamException, RecognitionException {
        System.out.println("SeamText -> Html -> SeamText");
        System.out.println("------------------- SeamText -------------------");
        final SeamTextParser seamParser = new SeamTextParser(new SeamTextLexer(
                new StringReader(seamTextExpression)));
        seamParser.startRule();
        
        System.out.println("------------------- Html -----------------------");
        final String html = seamParser.toString();
        System.out.println("html = " + html);
        
        System.out.println("------------------- SeamText -------------------");
        final String seamtext = convertHtmlToSeamText(html);
        
        assertEquals(seamTextExpression, seamtext.trim());
    }
    
    private String convertHtmlToSeamText(final String html) {
        try {
            return HtmlToSeamSAXParser.convertHtmlToSeamText(html);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        
        return null;
    }
}

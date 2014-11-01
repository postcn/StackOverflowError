package edu.rosehulman.johncena;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathTagWrapper extends EvalFunc<String> {

	@Override
	public String exec(Tuple arg0) throws IOException {
		String xml = (String) arg0.get(0);
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
			XPathExpression x = XPathFactory.newInstance().newXPath().compile("//@Tags");
			Node n = (Node) x.evaluate(doc, XPathConstants.NODE);
			return n == null ? null : n.toString().replace("Tags=", "").replace("\"", "");
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		//String xml = "<row Id=\"4\" PostTypeId=\"1\" AcceptedAnswerId=\"7\" CreationDate=\"2008-07-31T21:42:52.667\" Score=\"305\" ViewCount=\"20324\" Body=\"&lt;p&gt;I want to use a track-bar to change a form's opacity.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;This is my code:&lt;/p&gt;&#xA;&#xA;&lt;pre&gt;&lt;code&gt;decimal trans = trackBar1.Value / 5000;&#xA;this.Opacity = trans;&#xA;&lt;/code&gt;&lt;/pre&gt;&#xA;&#xA;&lt;p&gt;When I try to build it, I get this error:&lt;/p&gt;&#xA;&#xA;&lt;blockquote&gt;&#xA;  &lt;p&gt;Cannot implicitly convert type 'decimal' to 'double'.&lt;/p&gt;&#xA;&lt;/blockquote&gt;&#xA;&#xA;&lt;p&gt;I tried making &lt;code&gt;trans&lt;/code&gt; a &lt;code&gt;double&lt;/code&gt;, but then the control doesn't work. This code has worked fine for me in VB.NET in the past. &lt;/p&gt;&#xA;\" OwnerUserId=\"8\" LastEditorUserId=\"451518\" LastEditorDisplayName=\"Rich B\" LastEditDate=\"2014-07-28T10:02:50.557\" LastActivityDate=\"2014-07-28T10:02:50.557\" Title=\"When setting a form's opacity should I use a decimal or double?\" Tags=\"&lt;c#&gt;&lt;winforms&gt;&lt;type-conversion&gt;&lt;opacity&gt;\" AnswerCount=\"13\" CommentCount=\"1\" FavoriteCount=\"28\" CommunityOwnedDate=\"2012-10-31T16:42:47.213\" />";
		//String xml = "<row Id=\"6\" PostTypeId=\"1\" AcceptedAnswerId=\"31\" CreationDate=\"2008-07-31T22:08:08.620\" Score=\"130\" ViewCount=\"10031\" Body=\"&lt;p&gt;I have an absolutely positioned &lt;code&gt;div&lt;/code&gt; containing several children, one of which is a relatively positioned &lt;code&gt;div&lt;/code&gt;. When I use a &lt;strong&gt;percentage-based width&lt;/strong&gt; on the child &lt;code&gt;div&lt;/code&gt;, it collapses to &lt;code&gt;0&lt;/code&gt; width on IE7, but not on Firefox or Safari. &lt;/p&gt;&#xA;&#xA;&lt;p&gt;If I use &lt;strong&gt;pixel width&lt;/strong&gt;, it works. If the parent is relatively positioned, the percentage width on the child works. &lt;/p&gt;&#xA;&#xA;&lt;p&gt;Is there something I'm missing here? &#xA;Is there an easy fix for this besides the &lt;em&gt;pixel-based width&lt;/em&gt; on the child? &#xA;Is there an area of the &lt;code&gt;CSS&lt;/code&gt; specification that covers this?&lt;/p&gt;&#xA;\" OwnerUserId=\"9\" LastEditorUserId=\"1350209\" LastEditorDisplayName=\"Rich B\" LastEditDate=\"2014-06-26T04:57:13.750\" LastActivityDate=\"2014-08-17T17:32:41.700\" Title=\"Why doesn't the percentage width child in absolutely positioned parent work?\" Tags=\"&lt;html&gt;&lt;css&gt;&lt;css3&gt;&lt;internet-explorer-7&gt;\" AnswerCount=\"5\" CommentCount=\"0\" FavoriteCount=\"7\" />";
		String xml = "<row Id=\"7\" PostTypeId=\"2\" ParentId=\"4\" CreationDate=\"2008-07-31T22:17:57.883\" Score=\"234\" Body=\"&lt;p&gt;An explicit cast to double isn't necessary.&lt;/p&gt;&#xA;&#xA;&lt;pre&gt;&lt;code&gt;double trans = (double)trackBar1.Value / 5000.0;&#xA;&lt;/code&gt;&lt;/pre&gt;&#xA;&#xA;&lt;p&gt;Identifying the constant as &lt;code&gt;5000.0&lt;/code&gt; (or as &lt;code&gt;5000d&lt;/code&gt;) is sufficient:&lt;/p&gt;&#xA;&#xA;&lt;pre&gt;&lt;code&gt;double trans = trackBar1.Value / 5000.0;&#xA;double trans = trackBar1.Value / 5000d;&#xA;&lt;/code&gt;&lt;/pre&gt;&#xA;\" OwnerUserId=\"9\" LastEditorUserId=\"967315\" LastEditDate=\"2012-10-14T11:50:16.703\" LastActivityDate=\"2012-10-14T11:50:16.703\" CommentCount=\"0\" />";
		Tuple xmlTup = TupleFactory.getInstance().newTuple(xml);
		XPathTagWrapper wrap = new XPathTagWrapper();
		String out = wrap.exec(xmlTup);
		Tuple tagTup = TupleFactory.getInstance().newTuple(out);
		TagCleaner t = new TagCleaner();
		DataBag output = t.exec(tagTup);
		if (output == null) {
			System.out.println("Null output.");
			return;
		}
		System.out.println(output.toString());
		System.out.println(output.size());
	}
}

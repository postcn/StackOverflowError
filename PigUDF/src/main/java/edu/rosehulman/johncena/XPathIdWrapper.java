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
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathIdWrapper extends EvalFunc<Integer> {

	@Override
	public Integer exec(Tuple arg0) throws IOException {
		String xml = (String) arg0.get(0);
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
			XPathExpression x = XPathFactory.newInstance().newXPath().compile("//@Id");
			Node n = (Node) x.evaluate(doc, XPathConstants.NODE);
			return Integer.parseInt(n.toString().replace("Id=", "").replace("\"", ""));
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
		String xml = "<row Id=\"4\" PostTypeId=\"1\" AcceptedAnswerId=\"7\" CreationDate=\"2008-07-31T21:42:52.667\" Score=\"305\" ViewCount=\"20324\" Body=\"&lt;p&gt;I want to use a track-bar to change a form's opacity.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;This is my code:&lt;/p&gt;&#xA;&#xA;&lt;pre&gt;&lt;code&gt;decimal trans = trackBar1.Value / 5000;&#xA;this.Opacity = trans;&#xA;&lt;/code&gt;&lt;/pre&gt;&#xA;&#xA;&lt;p&gt;When I try to build it, I get this error:&lt;/p&gt;&#xA;&#xA;&lt;blockquote&gt;&#xA;  &lt;p&gt;Cannot implicitly convert type 'decimal' to 'double'.&lt;/p&gt;&#xA;&lt;/blockquote&gt;&#xA;&#xA;&lt;p&gt;I tried making &lt;code&gt;trans&lt;/code&gt; a &lt;code&gt;double&lt;/code&gt;, but then the control doesn't work. This code has worked fine for me in VB.NET in the past. &lt;/p&gt;&#xA;\" OwnerUserId=\"8\" LastEditorUserId=\"451518\" LastEditorDisplayName=\"Rich B\" LastEditDate=\"2014-07-28T10:02:50.557\" LastActivityDate=\"2014-07-28T10:02:50.557\" Title=\"When setting a form's opacity should I use a decimal or double?\" Tags=\"&lt;c#&gt;&lt;winforms&gt;&lt;type-conversion&gt;&lt;opacity&gt;\" AnswerCount=\"13\" CommentCount=\"1\" FavoriteCount=\"28\" CommunityOwnedDate=\"2012-10-31T16:42:47.213\" />";
		Tuple xmlTup = TupleFactory.getInstance().newTuple(xml);
		XPathIdWrapper wrap = new XPathIdWrapper();
		Integer out = wrap.exec(xmlTup);
		System.out.println(out);
	}
}

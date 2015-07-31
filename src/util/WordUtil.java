package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

import beans.Schedules;
import beans.Students;

public class WordUtil {

	public static WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
		  WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));
		  return template;
		 }
	
	public static  List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
		  List<Object> result = new ArrayList<Object>();
		  if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
		 
		  if (obj.getClass().equals(toSearch))
		   result.add(obj);
		  else if (obj instanceof ContentAccessor) {
		   List<?> children = ((ContentAccessor) obj).getContent();
		   for (Object child : children) {
		    result.addAll(getAllElementFromObject(child, toSearch));
		   }
		 
		  }
		  return result;
		 }
	
	public static void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder ) {
		  List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
		 
		  for (Object text : texts) {
		   Text textElement = (Text) text;
		   if (textElement.getValue().equals(placeholder)) {
		    textElement.setValue(name);
		   }
		  }
		 }
	
	public static void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
		 File f = new File(target);
		 template.save(f);
		}
    
	public static void replacePlaceholder1(WordprocessingMLPackage template, List<Students> ls, String placeholder ) {
		  List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
		 
		  for (Object text : texts) {
		   Text textElement = (Text) text;
		   if (textElement.getValue().equals(placeholder)) {
			   org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
			   int writableWidthTwips = template.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
				 int cols = 3;
				 int cellWidthTwips = new Double(Math.floor((writableWidthTwips / cols))).intValue();

				 // Tbl tbl = TblFactory.createTable(3, 3, cellWidthTwips);

				 Tbl tbl = Context.getWmlObjectFactory().createTbl();

				 // ///////////////////
				 String strTblPr = "<w:tblPr " + Namespaces.W_NAMESPACE_DECLARATION
						 + ">" + "<w:tblStyle w:val=\"TableGrid\"/>"
						 + "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
						 + "<w:tblLook w:val=\"04A0\"/>" + "</w:tblPr>";
				 TblPr tblPr = null;

				 try {
					 tblPr = (TblPr) XmlUtils.unmarshalString(strTblPr);
				 }

				 catch (JAXBException e) {
					 // Shouldn't happen
					 e.printStackTrace();
				 }
				 tbl.setTblPr(tblPr);

				 TblGrid tblGrid = Context.getWmlObjectFactory().createTblGrid();
				 tbl.setTblGrid(tblGrid);


				 	for (int i = 0; i < cols; i++) {
				 		TblGridCol gridCol = Context.getWmlObjectFactory().createTblGridCol();
				 		gridCol.setW(BigInteger.valueOf(cellWidthTwips));
				 		tblGrid.getGridCol().add(gridCol);
				 	}

				 	
				 	
				 	

				 	Tc tc = null;
				 	Tr tr = null;
				 	int rows = ls.size();
				 	for (int j = 0; j < rows; j++) {
				 		tr = Context.getWmlObjectFactory().createTr();
				 		Students sc = ls.get(j);
				 		ArrayList<String> arr = listToArr(sc);
				 		// tbl.getEGContentRowContent().add(tr);
				 		// for (int i = 1; i <= cols; i++) {
				 		for (int i = 0; i < cols; i++) {
				 			tc = Context.getWmlObjectFactory().createTc();

				 			TcPr tcPr = Context.getWmlObjectFactory().createTcPr();
				 			tc.setTcPr(tcPr);
				 			TblWidth cellWidth = Context.getWmlObjectFactory().createTblWidth();
				 			tcPr.setTcW(cellWidth);
				 			cellWidth.setType("dxa");
				 			cellWidth.setW(BigInteger.valueOf(cellWidthTwips));
				 			Context.getWmlObjectFactory();
				 			org.docx4j.wml.P p1 = factory.createP();
				 			org.docx4j.wml.Text t1 = factory.createText();
				 			// ls.add("val :" + i);
				 			t1.setValue(arr.get(i).toString());
				 			org.docx4j.wml.R run1 = factory.createR();
				 			run1.getRunContent().add(t1);

				 			p1.getParagraphContent().add(run1);
				 			tc.getEGBlockLevelElts().add(p1);
				 			tr.getEGContentCellContent().add(tc);

				 		}

				 		tbl.getEGContentRowContent().add(tr);
				 	}

				 	template.getMainDocumentPart().addObject(tbl);
		   }
		  }
		  File f = new File("C:\\Свидетельство_1111111.docx");
			 try {
				template.save(f);
			} catch (Docx4JException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	
	private static ArrayList<String> listToArr (Students s) {
		ArrayList<String> str = new ArrayList<String> ();
		str.add(s.getName());
		str.add(s.getSurname());
		str.add(s.getPatronymicname());
		return str;
	}

    /*public static void main(String[] args) {
        // TODO Auto-generated method stub
        String filepathString = "D:/2.doc";
        String destpathString = "D:/2ttt.doc";
        Map<String, String> map = new HashMap<String, String>();
        map.put("${NAME}", "王五王五啊王力宏的辣味回答侯卫东拉网");
        map.put("${NsAME}", "王五王五啊王力味回答侯卫东拉网");
        map.put("${NAMaE}", "王五王五啊王力宏侯卫东拉网");
        map.put("${NArME}", "王五王五啊王力宏的辣味回答东拉网");
        map.put("${NwAME}", "王五王五啊王的辣味回答侯卫东拉网");
        map.put("${NA4ME}", "王五王五啊王力侯卫东拉网");
        map.put("${N5AME}", "王五王五辣味回答侯卫东拉网");
        map.put("${NAadwME}", "王五力宏的辣味回答侯卫东拉网");
        System.out.println(replaceAndGenerateWord(filepathString,
                destpathString, map));
    }*/
}

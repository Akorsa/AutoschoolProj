package util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;

import beans.Schedules;
import beans.Teachers;


public class Test {
	
	
	public static void replaceAndGenerateWord(List<Schedules> ls) throws InvalidFormatException { 

			WordprocessingMLPackage wordMLPackage = null;
			String inputfilepath;
			boolean save;
			

	 try {
		 // getInputFilePath(args);
		 inputfilepath = System.getProperty("user.dir")
				 + "/CreateWordprocessingMLDocument_out.docx";
	 } catch (IllegalArgumentException e) {

		 inputfilepath = System.getProperty("user.dir")
				 + "/CreateWordprocessingMLDocument_out.docx";
	 }

	 save = (inputfilepath == null ? false : true);

	 System.out.println("Creating package..");
	 wordMLPackage = WordprocessingMLPackage.createPackage();

	 wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title","Hello world");

	 wordMLPackage.getMainDocumentPart().addParagraphOfText("from docx4j!");
	 org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();

	 // Let's add a table
	 int writableWidthTwips = wordMLPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
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

	 wordMLPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
	 new Double(
			 Math.floor((writableWidthTwips / cols))).intValue();

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
	 		Schedules sc = ls.get(j);
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

	 	wordMLPackage.getMainDocumentPart().addObject(tbl);

	 	if (save) {
	 		try {
				wordMLPackage.save(new java.io.File(inputfilepath));
			} catch (Docx4JException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		System.out.println("Saved " + inputfilepath);
	 	}

	 	System.out.println("Done.");

 	}
	
	private static ArrayList<String> listToArr (Schedules s) {
		ArrayList<String> str = new ArrayList<String> ();
		str.add(s.getGroups().getGroupName());
		str.add(s.getTeachers().getPrepodfam());
		str.add(s.getThemes().getNameOfTheme());
		return str;
	}
 
 

}

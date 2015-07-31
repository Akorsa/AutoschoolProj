package frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import beans.Tests;

/**
 * A simple demonstration application showing how to create a line chart using data from a
 * {@link CategoryDataset}.
 */
public class LineChartDemo1 extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6749559657107074972L;
	private List<Tests> test;

	/**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public LineChartDemo1(final String title,List<Tests> test) {
    	super(title);
    	this.test = test;
    	XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset.
     * 
     * @return The dataset.
     */
    private XYDataset createDataset() {

    	final TimeSeriesCollection dataset = new TimeSeriesCollection();
    	dataset.setDomainIsPointsInTime(true);
    	Calendar cal = Calendar.getInstance();
    
    	 final TimeSeries s1 = new TimeSeries("");
        for(Tests object : test)
        {
        	cal.setTimeInMillis(object.getTimeOfTest().getTime());
        	//System.out.println(cal.get(Calendar.MONTH));
        	Minute d1 = new Minute(cal.get(Calendar.MINUTE),cal.get(Calendar.HOUR_OF_DAY),
        			cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR));
        	//System.out.println(dateFormat.format(date) + " "+ object.getBall());
        	s1.add(d1, object.getBall());
        	//series.addValue(, object.getTimeOfTest().toString());
           //System.out.print("First Name: " + row.get("surname")); 
           //System.out.println(", Salary: " + row.get("summ")); 
        }
        dataset.addSeries(s1);

        return dataset;

    }
    
    private JFreeChart createChart(XYDataset dataset) {

    	 final JFreeChart chart = ChartFactory.createTimeSeriesChart(
    	            "График результатов тестирования",
    	            "Дата тестирования", 
    	            "Уровень знаний",
    	            dataset,
    	            false,
    	            true,
    	            false
    	        );
    	 		chart.getTitle().setFont((new Font("Arial", Font.PLAIN, 15)));

    	        chart.setBackgroundPaint(Color.white);
    	        
//    	        final StandardLegend sl = (StandardLegend) chart.getLegend();
    	  //      sl.setDisplaySeriesShapes(true);

    	        final XYPlot plot = chart.getXYPlot();
    	        //plot.setOutlinePaint(null);
    	        plot.setBackgroundPaint(Color.lightGray);
    	        plot.setDomainGridlinePaint(Color.white);
    	        plot.setRangeGridlinePaint(Color.white);
    	    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
    	        plot.setDomainCrosshairVisible(true);
    	        plot.setRangeCrosshairVisible(false);
    	        
    	        final XYItemRenderer renderer = plot.getRenderer();
    	        if (renderer instanceof StandardXYItemRenderer) {
    	            final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
    	            //rr.setPlotShapes(true);
    	            //rr.setShapesFilled(true);
    	            renderer.setSeriesStroke(0, new BasicStroke(2.0f));
    	            renderer.setSeriesStroke(1, new BasicStroke(2.0f));
    	           }
    	        
    	        final DateAxis axis = (DateAxis) plot.getDomainAxis();
    	        axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy HH:mm"));
    	        axis.setTickLabelFont(new Font("Helvetica",Font.PLAIN, 10));
    		    //axis.setAutoRange(true);
    		    axis.setFixedAutoRange(200000000.0);  // 2 seconds
    	        axis.setVerticalTickLabels(true);
    	        return chart;
        
    }
    

}
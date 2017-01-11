package chap9.java.science.data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;

import javax.swing.JFrame;

import de.erichseifert.gral.data.*;
import de.erichseifert.gral.data.comparators.Ascending;
import de.erichseifert.gral.data.filters.*;
import de.erichseifert.gral.data.filters.Filter.Mode;
import de.erichseifert.gral.io.data.*;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class DataFiltering extends JFrame {

    public DataFiltering() throws IOException {
        DataSource source = readData("text/tab-separated-values");
        DataTable data = new DataTable(source);
        data.sort(new Ascending(0));

        Kernel kernel = KernelUtils.getUniform(30, 15, 1.0).normalize();
        DataSource filtered = new Convolution(data, kernel, Mode.REPEAT, 1);

        writeData(filtered, "myDataFiltered.csv");

        XYPlot plot = new XYPlot(data, filtered);

        plot.setPointRenderer(filtered, null);
        DefaultLineRenderer2D lineRenderer = new DefaultLineRenderer2D();
        lineRenderer.setColor(Color.BLUE);
        plot.setLineRenderer(filtered, lineRenderer);

        plot.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));
        getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(getContentPane().getMinimumSize());
        setSize(800, 400);
    }

    private static DataSource readData(String filename) throws IOException {
        FileInputStream dataStream = new FileInputStream(filename);
        DataReaderFactory factory = DataReaderFactory.getInstance();
        DataReader reader = factory.get("text/tab-separated-values");
        try {
            DataSource data = reader.read(dataStream, Double.class, Double.class);
            return data;
        } finally {
            dataStream.close();
        }
    }

    private static void writeData(DataSource data, String filename) throws IOException {
        FileOutputStream dataStream = new FileOutputStream(filename);
        DataWriterFactory factory = DataWriterFactory.getInstance();
        DataWriter writer = factory.get("text/tab-separated-values");
        try {
            writer.write(data, dataStream);
        } finally {
            dataStream.close();
        }
    }

    public static void main(String[] args) throws IOException {
        DataFiltering df = new DataFiltering();
        df.setVisible(true);
    }
}
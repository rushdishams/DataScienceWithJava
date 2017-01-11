/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2013 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <michael[at]erichseifert.de>
 *
 * This file is part of GRAL.
 *
 * GRAL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GRAL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GRAL.  If not, see <http://www.gnu.org/licenses/>.
 */
package chap9.java.science.data;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.io.data.DataReader;
import de.erichseifert.gral.io.data.DataReaderFactory;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class LinePlotting extends JFrame {
	private static final long serialVersionUID = 1L;

	public LinePlotting() throws FileNotFoundException, IOException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1600, 1400);

		DataReader reader = DataReaderFactory.getInstance().get("text/tab-separated-values");
		DataSource data = reader.read(new FileInputStream("text/tab-separated-values"), Double.class, Double.class);
		XYPlot plot = new XYPlot(data);
		getContentPane().add(new InteractivePanel(plot));
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(data, lines);
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(data).setColor(color);
		plot.getLineRenderer(data).setColor(color);
	}

	public static void main(String[] args) {
		LinePlotting frame = null;
		try {
			frame = new LinePlotting();
		} catch (IOException e) {
		}
		frame.setVisible(true);
	}
}
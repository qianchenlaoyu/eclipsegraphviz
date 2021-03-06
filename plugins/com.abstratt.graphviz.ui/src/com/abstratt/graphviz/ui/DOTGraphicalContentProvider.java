package com.abstratt.graphviz.ui;

import static com.abstratt.imageviewer.IGraphicalContentProvider.GraphicFileFormat.*;

import java.io.ByteArrayInputStream;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.abstratt.graphviz.GraphViz;
import com.abstratt.imageviewer.AbstractGraphicalContentProvider;

/**
 * A graphical content provider that produces graphical output from a DOT
 * description.
 */
public class DOTGraphicalContentProvider extends AbstractGraphicalContentProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.abstratt.imageviewer.IGraphicalContentProvider#loadImage(org.eclipse
     * .swt.widgets.Display, org.eclipse.swt.graphics.Point, java.lang.Object)
     */
    public Image loadImage(Display display, Point desiredSize, Object newInput) throws CoreException {
        if (desiredSize == null)
            desiredSize = new Point(0, 0);
        byte[] imageContents = GraphViz.load(new ByteArrayInputStream((byte[]) newInput), "png", desiredSize.x,
                desiredSize.y);
        // try to load the resulting image
        ImageLoader loader = new ImageLoader();
        ImageData[] imageData = loader.load(new ByteArrayInputStream(imageContents));
        return new Image(Display.getDefault(), imageData[0]);
    }

    @Override
    public void saveImage(Display display, Point suggestedSize, Object input, IPath outputLocation, GraphicFileFormat fileFormat)
            throws CoreException {
        if (suggestedSize == null)
            suggestedSize = new Point(0, 0);
        String outputFormat = fileFormat.getExtension();
        GraphViz.generate(new ByteArrayInputStream((byte[]) input), outputFormat, suggestedSize.x, suggestedSize.y,
                outputLocation);
    }
    
    @Override
	public Set<GraphicFileFormat> getSupportedFormats() {
    	return EnumSet.of(BITMAP, GIF, TIFF, JPEG, PNG, SVG, DOT);
    }
}

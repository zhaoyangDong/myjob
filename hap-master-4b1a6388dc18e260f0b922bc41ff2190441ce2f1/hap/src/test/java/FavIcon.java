import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class FavIcon {

	public static void main(String[] args) throws Exception {
		gen();
	}

	public static void gen() throws IOException {

		int width = 32, height = 32;

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();

		g2d.setComposite(AlphaComposite.Clear);

		g2d.fillRect(0, 0, width, height);
		g2d.setComposite(AlphaComposite.Src);

		g2d.setColor(new Color(41, 143, 204));

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int ml = 1, mt = 1, r = 4;

		g2d.fillRoundRect(ml, mt, width - ml * 2, height - mt * 2, r, r);

		// draw H
		g2d.setColor(new Color(240, 240, 240));
		int w = width / 8, l = height / 4, t = (w + l) / 2;

		g2d.fillRect(l, t, w, height - t * 2);
		g2d.fillRect(width - w - l, t, w, height - t * 2);
		g2d.fillRect(l, height / 2 - w / 2, width - l * 2, w);

		g2d.dispose();
		ImageIO.write(bufferedImage, "PNG", new File(System.getProperty("user.home"), "Downloads/favicon.png"));
	}

}

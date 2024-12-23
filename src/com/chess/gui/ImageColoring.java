package com.chess.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Piece;

public class ImageColoring {
	public static BufferedImage getImage(Piece piece) {
		String defaultPieceImagesPath = "art/pieces/warriors/";
		try {
			// Read the input image
			BufferedImage img = ImageIO.read(new File(defaultPieceImagesPath + "W" + piece + ".png"));
			if (piece.getAlliance() == Alliance.WHITE) {
				return img;
			}

			// Process the image
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					int pixel = img.getRGB(x, y);

					// Extract the RGB components
					Color color = new Color(pixel, true); // Use true to retain alpha

					// Check if the pixel is white (R=255, G=255, B=255)
					if (isWhite(color)) {
						// Replace white with light blue, keeping the alpha
						int newColor = new Color(piece.getAlliance().getColor().getRed(),
								piece.getAlliance().getColor().getGreen(), piece.getAlliance().getColor().getBlue(),
								color.getAlpha()).getRGB();
						img.setRGB(x, y, newColor);
					} else if (isBlack(color) && piece.getAlliance().isWhite()) {
						int newColor = new Color(255, 255, 255, color.getAlpha()).getRGB();
						img.setRGB(x, y, newColor);
					}
				}
			}

			// Write the output image to file
			return img;

		} catch (Exception e) {
			try {
				// Read the input image
				BufferedImage img = ImageIO.read(new File(defaultPieceImagesPath + "W" + piece + ".gif"));
				if (piece.getAlliance() == Alliance.WHITE) {
					return img;
				}

				// Process the image
				for (int y = 0; y < img.getHeight(); y++) {
					for (int x = 0; x < img.getWidth(); x++) {
						int pixel = img.getRGB(x, y);

						// Extract the RGB components
						Color color = new Color(pixel, true); // Use true to retain alpha

						// Check if the pixel is white (R=255, G=255, B=255)
						if (isWhite(color)) {
							// Replace white with light blue, keeping the alpha
							int newColor = new Color(piece.getAlliance().getColor().getRed(),
									piece.getAlliance().getColor().getGreen(), piece.getAlliance().getColor().getBlue(),
									color.getAlpha()).getRGB();
							img.setRGB(x, y, newColor);
						} else if (isBlack(color) && piece.getAlliance().isWhite()) {
							int newColor = new Color(255, 255, 255, color.getAlpha()).getRGB();
							img.setRGB(x, y, newColor);
						}
					}
				}

				// Write the output image to file
				return img;

			} catch (Exception exception) {
				try {
					// Read the input image
					BufferedImage img = ImageIO.read(new File(defaultPieceImagesPath + "B" + piece + ".png"));
					if (piece.getAlliance() == Alliance.BLACK) {
						return img;
					}

					// Process the image
					for (int y = 0; y < img.getHeight(); y++) {
						for (int x = 0; x < img.getWidth(); x++) {
							int pixel = img.getRGB(x, y);

							// Extract the RGB components
							Color color = new Color(pixel, true); // Use true to retain alpha

							// Check if the pixel is white (R=255, G=255, B=255)
							if (isBlack(color)) {
								// Replace white with light blue, keeping the alpha
								int newColor = new Color(piece.getAlliance().getColor().getRed(),
										piece.getAlliance().getColor().getGreen(),
										piece.getAlliance().getColor().getBlue(), color.getAlpha()).getRGB();
								img.setRGB(x, y, newColor);
							} else if (isWhite(color) && piece.getAlliance().isBlack()) {
								int newColor = new Color(255, 255, 255, color.getAlpha()).getRGB();
								img.setRGB(x, y, newColor);
							}
						}
					}

					// Write the output image to file
					return img;

				} catch (Exception exception2) {
					try {
						// Read the input image
						BufferedImage img = ImageIO.read(new File(defaultPieceImagesPath + "B" + piece + ".gif"));
						if (piece.getAlliance() == Alliance.BLACK) {
							return img;
						}

						// Process the image
						for (int y = 0; y < img.getHeight(); y++) {
							for (int x = 0; x < img.getWidth(); x++) {
								int pixel = img.getRGB(x, y);

								// Extract the RGB components
								Color color = new Color(pixel, true); // Use true to retain alpha

								// Check if the pixel is white (R=255, G=255, B=255)
								if (isBlack(color)) {
									// Replace white with light blue, keeping the alpha
									int newColor = new Color(piece.getAlliance().getColor().getRed(),
											piece.getAlliance().getColor().getGreen(),
											piece.getAlliance().getColor().getBlue(), color.getAlpha()).getRGB();
									img.setRGB(x, y, newColor);
								} else if (isWhite(color) && piece.getAlliance().isBlack()) {
									int newColor = new Color(255, 255, 255, color.getAlpha()).getRGB();
									img.setRGB(x, y, newColor);
								}
							}
						}

						// Write the output image to file
						return img;

					} catch (Exception exception3) {
						exception.printStackTrace();
					}
				}
			}
		}
		throw new RuntimeException("Could not create Image");
	}

	private static boolean isWhite(Color color) {
		int margin = 75; // Allow a margin of +/- 10 for each RGB component
		return Math.abs(color.getRed() - 255) <= margin && Math.abs(color.getGreen() - 255) <= margin
				&& Math.abs(color.getBlue() - 255) <= margin;
	}

	private static boolean isBlack(Color color) {
		int margin = 20; // Allow a margin of +/- 10 for each RGB component
		return Math.abs(color.getRed() - 0) <= margin && Math.abs(color.getGreen() - 0) <= margin
				&& Math.abs(color.getBlue() - 0) <= margin;
	}

}

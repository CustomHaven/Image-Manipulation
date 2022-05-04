// https://www.codecademy.com/courses/learn-java/projects/2-d-arrays-image-manipulation-project
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
    public static void main(String[] args) {
        // The provided images are apple.jpg, flower.jpg, and kitten.jpg
        int[][] imageData = imgToTwoD("images/apple.jpg");
        // Or load your own image using a URL!
        // int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
        // viewImageData(imageData);

        int[][] trimmed = trimBorders(imageData, 60);
        twoDToImage(trimmed, "./trimmed_apple.jpg");
        int[][] negColor = negativeColor(imageData);
        twoDToImage(negColor, "./negative_apple.jpg");
        int[][] stretchedHImg = stretchHorizontally(imageData);
        twoDToImage(stretchedHImg, "./stretch_apple.jpg");
        int[][] shrinkImg = shrinkVertically(imageData);
        twoDToImage(shrinkImg, "./shrink_apple.jpg");
        int[][] invertedImg = invertImage(imageData);
        twoDToImage(invertedImg, "./inverted_apple.jpg");
        int[][] filtered = colorFilter(imageData, 75,30,30);
        twoDToImage(filtered, "./filtered_apple.jpg");

        // int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
        // Painting with pixels

        int[][] blankImg = new int[500][500];
        int[][] randomImg = paintRandomImage(blankImg);
        twoDToImage(randomImg, "./random_img.jpg");

        int[] rgba = {255, 255, 0, 255};
        int[][] rectangleImg = paintRectangle(randomImg, 200, 200, 100, 100, getColorIntValFromRGBA(rgba));
        twoDToImage(rectangleImg, "./rectangle.jpg");

        int[][] generatedRectangles = generateRectangles(randomImg, 1000);

        twoDToImage(generatedRectangles, "./generated_rect.jpg");
    }
    // Image Processing Methods
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
        // Example Method
        if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
            int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
            for (int i = 0; i < trimmedImg.length; i++) {
                for (int j = 0; j < trimmedImg[i].length; j++) {
                    trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
                }
            }
            return trimmedImg;
        } else {
            System.out.println("Cannot trim that many pixels from the given image.");
            return imageTwoD;
        }
    }
    public static int[][] negativeColor(int[][] imageTwoD) {
        //Q10-Q15 TODO: Fill in the code for this method
        int[][] negativeImage = new int[imageTwoD.length][imageTwoD[0].length];

        for (int o = 0; o < negativeImage.length; o++) {
            for (int i = 0; i < negativeImage[o].length; i++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[o][i]);

                rgba[0] = 255 - rgba[0];
                rgba[1] = 255 - rgba[1];
                rgba[2] = 255 - rgba[2];

                negativeImage[o][i] = getColorIntValFromRGBA(rgba);
            }
        }
        return negativeImage;
    }
    public static int[][] stretchHorizontally(int[][] imageTwoD) {
        //Q16-Q20 TODO: Fill in the code for this method
        int[][] manipulatedImage = new int[imageTwoD.length][imageTwoD[0].length*2];
        int it = 0;
        for (int o = 0; o < imageTwoD.length; o++) {
            for (int i = 0; i < imageTwoD[o].length; i++) {
                it = i * 2;
                manipulatedImage[o][it] = imageTwoD[o][i];
                manipulatedImage[o][it + 1] = imageTwoD[o][i];
            }
        }
        return manipulatedImage;
    }
    public static int[][] shrinkVertically(int[][] imageTwoD) {
        //Q21-Q25 TODO: Fill in the code for this method
        int[][] manipulatedImg = new int[imageTwoD.length / 2][imageTwoD[0].length];

        for (int i = 0; i < imageTwoD[0].length; i++) {
            for (int o = 0; o < imageTwoD.length; o+=2) {
                manipulatedImg[o/2][i] = imageTwoD[o][i];
            }
        }

        return manipulatedImg;
    }
    public static int[][] invertImage(int[][] imageTwoD) {
        //Q26-Q30 TODO: Fill in the code for this method
        int[][] flippedImg = new int[imageTwoD.length][imageTwoD[0].length];

        for (int r = 0; r < imageTwoD.length; r++) {
            for (int c = 0; c < imageTwoD[r].length; c++) {
                flippedImg[r][c] = imageTwoD[(imageTwoD.length-1) -r][(imageTwoD[r].length-1) -c];
            }
        }
        return flippedImg;
    }
    public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
        //Q31-Q39 TODO: Fill in the code for this method
        int[][] manipulatedImg = new int[imageTwoD.length][imageTwoD[0].length];

        for (int r = 0; r < imageTwoD.length; r++) {
            for (int c = 0; c < imageTwoD[r].length; c++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[r][c]);

                int newRed = rgba[0] - redChangeValue;
                int newGreen = rgba[1] + greenChangeValue;
                int newBlue = rgba[2] - blueChangeValue;

                if (newRed < 0) {
                    newRed = 0;
                }
                if (newGreen < 0) {
                    newGreen = 0;
                }
                if (newBlue < 0) {
                    newBlue = 0;
                }

                if (newRed > 255) {
                    newRed = 255;
                }
                if (newGreen > 255) {
                    newGreen = 255;
                }
                if (newBlue > 255) {
                    newBlue = 255;
                }

                rgba[0] = newRed;
                rgba[1] = newGreen;
                rgba[2] = newBlue;

                manipulatedImg[r][c] = getColorIntValFromRGBA(rgba);

            }
        }
        return manipulatedImg;
    }
    // Painting Methods
    public static int[][] paintRandomImage(int[][] canvas) {
        //Q40-Q47 TODO: Fill in the code for this method
        Random rand = new Random();

        for (int r = 0; r < canvas.length; r++) {
            for (int c = 0; c < canvas[r].length; c++) {
                int[] rgba = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};
                canvas[r][c] = getColorIntValFromRGBA(rgba);
            }
        }

        return canvas;
    }
    public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
        //Q48-Q54 TODO: Fill in the code for this method
        for (int r = 0; r < canvas.length; r++) {
            for (int c = 0; c < canvas[r].length; c++) {
                if (r>=rowPosition && r<=rowPosition+width) {
                    if (c>=colPosition && c<=colPosition+height) {
                        canvas[r][c] = color;
                    }
                }
            }
        }
        return canvas;
    }
    public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
        //Q55-Q63 TODO: Fill in the code for this method
        Random rand = new Random();
        for (int i = 0; i < numRectangles; i++) {
            int randomWidth = rand.nextInt(canvas[0].length);
            int randomHeight = rand.nextInt(canvas.length);

            int randomRowPos = rand.nextInt(canvas.length-randomHeight);
            int randomColPos = rand.nextInt(canvas[0].length-randomWidth);

            int[] randRGBA = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};

            int randomColor = getColorIntValFromRGBA(randRGBA);

            canvas = paintRectangle(canvas, randomWidth, randomHeight, randomRowPos, randomColPos, randomColor);
        }
        return canvas;
    }
    // Utility Methods
    public static int[][] imgToTwoD(String inputFileOrLink) {
        BufferedImage image = null;
        try {
            if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
                URL imageUrl = new URL(inputFileOrLink);
                image = ImageIO.read(imageUrl);
                if (image == null) {
                    System.out.println("Failed to get image from provided URL.");
                }
            } else {
                File inputFile = new File(inputFileOrLink);
                image = ImageIO.read(inputFile);
            }
            int imgRows = image.getHeight();
            int imgCols = image.getWidth();
            int[][] pixelData = new int[imgRows][imgCols];
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    pixelData[i][j] = image.getRGB(j, i);
                }
            }
            return pixelData;
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getLocalizedMessage());
            return null;
        }
    }
    public static void twoDToImage(int[][] imgData, String fileName) {
        try {
            int imgRows = imgData.length;
            int imgCols = imgData[0].length;
            BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    result.setRGB(j, i, imgData[i][j]);
                }
            }
            File output = new File(fileName);
            ImageIO.write(result, "jpg", output);
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getLocalizedMessage());
        }
    }
    public static int[] getRGBAFromPixel(int pixelColorValue) {
        Color pixelColor = new Color(pixelColorValue);
        return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
    }
    public static int getColorIntValFromRGBA(int[] colorData) {
        if (colorData.length == 4) {
            Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
            return color.getRGB();
        } else {
            System.out.println("Incorrect number of elements in RGBA array.");
            return -1;
        }
    }
    public static void viewImageData(int[][] imageTwoD) {
        if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
            int[][] rawPixels = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rawPixels[i][j] = imageTwoD[i][j];
                }
            }
            System.out.println("Raw pixel data from the top left corner.");
            System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
            int[][][] rgbPixels = new int[3][3][4];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
                }
            }
            System.out.println();
            System.out.println("Extracted RGBA pixel data from top the left corner.");
            for (int[][] row : rgbPixels) {
                System.out.print(Arrays.deepToString(row) + System.lineSeparator());
            }
        } else {
            System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
        }
    }
}
public class ImageManipulationMethods {
    public static void main(String[] args) {
        int image[][][], flippedImage[][][], grayscaledImage[][][], rotatedImage[][][];

        image=PPMInputOutput.readPPMFile("cake.ppm");

        flippedImage=verticalFlip(image);
        PPMInputOutput.writePPMFile("flippedCake.ppm",flippedImage);

        grayscaledImage=greyScale(image);
        PPMInputOutput.writePPMFile("greyCake.ppm",grayscaledImage);

        rotatedImage=rotateClockwise(image);
        PPMInputOutput.writePPMFile("rotatedCake.ppm",rotatedImage);



    }

    /**
     * This method vertically flips RGB image array
     * k is countercounter used in order to write forst row of real image into last row od flipped image and so on.
     * @param imageArray
     * @return
     */
    public static int [][][] verticalFlip(int [][][] imageArray){
        int rows, columns, k;

        rows=imageArray.length;
        columns=imageArray[0].length;
        k=rows-1;
        int[][][] flippedArray=new int[rows][columns][3];

        for (int i= 0; i<rows;i++){
            for (int j= 0; j<columns;j++) {
                flippedArray[k][j]=imageArray[i][j];
            }
            k--;
        }
        System.out.println("Your image has been vertically flipped.");
        return flippedArray;
    }

    /**
     * This method rotates RGB image array 90 degrees clockwise
     * @param imageArray
     * @return
     */
    public static int [][][] rotateClockwise(int [][][] imageArray){
        int newRows, newColumns;
        newRows=imageArray[0].length;
        newColumns=imageArray.length;
        int[][][]rotatedImage=new int[newRows][newColumns][3];

        newColumns=newColumns-1;
        for(int i=0;i<imageArray.length;i++){
            newRows=0;
            for(int j=0;j<imageArray[0].length;j++){
                rotatedImage[newRows][newColumns]=imageArray[i][j];
                newRows++;
            }
            newColumns--;
        }
        System.out.println("Your image has been rotated 90 degrees clockwise.");

        return rotatedImage;
    }

    /**
     * This method converts image into grayscaled using average method
     * @param imageArray
     * @return
     */
    public static int[][][] greyScale(int[][][] imageArray){
        int rows, columns, sum, avg;
        int[][][]grayscaledImage=new int[imageArray.length][imageArray[0].length][3];

        for(int i=0;i<imageArray.length;i++){
            for(int j=0;j<imageArray[0].length;j++){
                sum=0;
                for(int k=0; k<3;k++){
                    sum+=imageArray[i][j][k];
                }
                avg=sum/3;
                for(int k=0;k<3;k++){
                    grayscaledImage[i][j][k]=avg;
                }
            }
        }
        System.out.println("Your image has been grey scaled by average method.");
        return grayscaledImage;
    }
}

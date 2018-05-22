
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ANN
{
float[][] inhid,hidout;
float bias=0,o_bias=0,outputlayer;
int[] target;
List<Integer> actual_testresults=new ArrayList<Integer>();
List<Integer> expected_testresults=new ArrayList<Integer>();
float[] inputlayer,hidden;
float[][]  input_image;
int[] bee_result;
int numberoffilestrain;
float n=0.01f;
int image_size=3072;
int numneurons=200;


// Assigning random weights
	public void build()
	{
		inhid=new float[image_size][numneurons];
		hidout=new float[numneurons][1];
		hidden=new float[numneurons];

		inputlayer=new float[image_size];
         for(int i=0;i<image_size;i++)
		{
			for(int j=0;j<numneurons;j++)
			{
				inhid[i][j]=(float)Math.random()-(float)0.5;
			}
		}

	for(int i=0;i<numneurons;i++)
		{

				hidout[i][0]=(float)Math.random()-(float)0.5;

		}


	}
	// training neural network selecting random inputs and weights.. final weight
	//vector is stored in inhid and hidout matrices
	public void train(int numIterations)
	{
		//System.out.println("in train");
		float h = 0,op=0,p=0;
		float deltah[]=new float[numneurons];
		float deltak=0;
		while(numIterations>0)
		{

			 int index=(int) Math.floor(numberoffilestrain*Math.random());
			 for(int i=0;i<image_size;i++)
				{
					inputlayer[i]=input_image[index][i];


				}

			for(int i=0;i<numneurons;i++)
			{
				h=0;
				for(int j=0;j<image_size;j++)
				{
					 h=h+inputlayer[j]*inhid[j][i];
				}
				h=h+bias*1;
				hidden[i]=(float) ((float)1/(1+Math.exp(-h)));
			}

							op=0;
				for(int j=0;j<numneurons;j++)
				{
					op=op+hidden[j]*hidout[j][0];
				}
				op=op+o_bias*1;
				outputlayer=(float) ((float)1/(1+Math.exp(-op)));



					 deltak=outputlayer*(1-outputlayer)*(bee_result[index]-outputlayer);
					// System.out.println("deltak"+ deltak[j]+"target"+target[j]);



			 for(int i=0;i<numneurons;i++)
			 {
				 p=0;

              p=p+hidout[i][0]*deltak;

			 deltah[i]=hidden[i]*(1-hidden[i])*p;
			 }

			 for(int i=0;i<numneurons;i++)
			 {
				 	 hidout[i][0]=hidout[i][0]+(n*deltak*hidden[i]);


			 }

			 for(int i=0;i<image_size;i++)
			 {
				 for(int j=0;j<numneurons;j++)
				 {
					 inhid[i][j]=inhid[i][j]+(n*deltah[j]*inputlayer[i]);

				 }


			 }

	     numIterations--;
		}

	}
	public float fit(String input)
	{
	      Imgcodecs imageCodecs = new Imgcodecs();
			float h=0,op=0;

	      // Reading the image
	      Mat src = imageCodecs.imread(input);
	      src.convertTo(src, CvType.CV_64FC3);

	      double[] data1 = new double[(int) (src.total()*src.channels())];
	      src.get(0, 0, data1);
	      for(int j=0;j<data1.length;j++)
	    	  inputlayer[j]=(float) (data1[j]/(float)255);

		for(int i=0;i<numneurons;i++)
		{
			h=0;
			for(int j=0;j<image_size;j++)
			{
				 h=h+inputlayer[j]*inhid[j][i];
			}
			h=h+bias*1;
			hidden[i]=(float) ((float)1/(1+Math.exp(-h)));
		}
			op=0;
			for(int j=0;j<numneurons;j++)
			{
				op=op+hidden[j]*hidout[j][0];
			}
			op=op+o_bias*1;
			outputlayer=(float) ((float)1/(1+Math.exp(-op)));
			if(outputlayer>=(float)0.5)
				outputlayer=1;
			else
				outputlayer=0;
    return outputlayer;
	}


	public void save()
			throws IOException
	{
		FileOutputStream fos = new FileOutputStream("D:/work/bees.txt");
	      ObjectOutputStream oos = new ObjectOutputStream(fos);
	      oos.writeObject(inhid);
	      oos.writeObject(hidout);
    }
	public void restore(String netpath) throws IOException, ClassNotFoundException
	{
		//build();
		FileInputStream fin = new FileInputStream(netpath);
	      ObjectInputStream oos = new ObjectInputStream(fin);
	      inhid=(float[][]) oos.readObject();
	      hidout=(float[][]) oos.readObject();

	}

	public void loadTrainData()
	{
		try
		{
	      //System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	      // Instantiating the imagecodecs class
	      Imgcodecs imageCodecs = new Imgcodecs();
	    	  List<String> textFiles = new ArrayList<String>();
	    	  File dir = new File("D:/train/single_bee_train");
	    	  String d="D:/train/single_bee_train/";
	    	  for (File file : dir.listFiles())
	    	  {
	    	    if (file.getName().endsWith((".png")))
	    	    textFiles.add(file.getName());

	    	  }
	    	  List<String> textFiles1 = new ArrayList<String>();

	    	  File dir1 = new File("D:/train/no_bee_train");
	    	  String d1="D:/train/no_bee_train/";
	    	  for (File file : dir1.listFiles())
	    	  {
	    	    if (file.getName().endsWith((".png")))

	    	      textFiles1.add(file.getName());

	    	    }
	    input_image=new float[textFiles.size()+textFiles1.size()][1024*3];
	    bee_result=new int[textFiles.size()+textFiles1.size()];
	    numberoffilestrain=textFiles.size()+textFiles1.size();
	    	  for(int i=0;i<textFiles.size();i++)
	    	  {
	      String input = textFiles.get(i);
	      // Reading the image
	      Mat src = imageCodecs.imread(d+input);
	      src.convertTo(src, CvType.CV_64FC3);

	      double[] data1 = new double[(int) (src.total()*src.channels())];
	      src.get(0, 0, data1);
	      float[] data2=new float[(int) (src.total()*src.channels())];
	      for(int j=0;j<data1.length;j++)
	    	  data2[j]=(float) (data1[j]/(float)255);

	      input_image[i]=data2;
	      bee_result[i]=1;
	         }

	    	  for(int i=0;i<textFiles1.size();i++)
	    	  {
	      String input = textFiles1.get(i);
	      // Reading the image
	      Mat src = imageCodecs.imread(d1+input);
	      src.convertTo(src, CvType.CV_64FC3);

	      double[] data1 = new double[(int) (src.total()*src.channels())];
	      src.get(0, 0, data1);
	      float[] data2=new float[(int) (src.total()*src.channels())];
	      for(int j=0;j<data1.length;j++)
	    	  data2[j]=(float) (data1[j]/(float)255);

	      input_image[i+textFiles.size()]=data2;
	      bee_result[i+textFiles.size()]=0;
	         }

	    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}

	public void accuracy()
	{
		int bees_classified=0,no_bees_classified=0;
		int total_bees=0, total_nobees=0;
	for(int i=0;i<actual_testresults.size();i++)
	{
		if(actual_testresults.get(i)==expected_testresults.get(i))
		{
			if(actual_testresults.get(i)==1)
				bees_classified++;
			else
				no_bees_classified++;
		}
		if(expected_testresults.get(i)==1)
			total_bees++;
		else
			total_nobees++;

			}

	float bees_classified_per=(float)bees_classified/total_bees;
	float nobees_classified_per=(float)no_bees_classified/total_nobees;
	System.out.println("percentage of bees images correctly classified:  "+bees_classified_per*100+"%");
	System.out.println("percentage of no bees images correctly classified:  "+nobees_classified_per*100+"%");

	}

   public void testNet(String netpath, String dirpath) throws ClassNotFoundException, IOException
   {
	   //build();// to initalize all data
	   restore(netpath);
	   float result=0;
	   try
		{
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	      // Instantiating the imagecodecs class
	      Imgcodecs imageCodecs = new Imgcodecs();
	    	  String dirpath1=dirpath+"/single_bee_test";
	    	  String d=dirpath+"/single_bee_test/";
	    	  File dir = new File(dirpath1);
           for (File file : dir.listFiles())
	    	  {
	    	    if (file.getName().endsWith((".png")))
	    	    {
	    	    result=fit(d+file.getName());
	    	    System.out.println("filename is : "+d+file.getName()+" result : "+result);
	    	    expected_testresults.add(1);
	    	    actual_testresults.add((int) result);
	    	    }
	           }
	    	  String dirpath2=dirpath+"/no_bee_test";
               d=dirpath+"/no_bee_test/";
	    	  File dir1 = new File(dirpath2);
	    	  for (File file : dir1.listFiles())
	    	  {
	    	    if (file.getName().endsWith((".png")))
	    	    {
		    	    result=fit(d+file.getName());
		    	    System.out.println("filename is : "+d+file.getName()+" result : "+result);
		    	    expected_testresults.add(0);
		    	    actual_testresults.add((int) result);
		    	}


	    	    }
	   		}
		catch (Exception e)
		{
			e.printStackTrace();
		}



   }
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		ANN an=new ANN();
	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

		System.out.println("Loading training data");
		an.loadTrainData();
		System.out.println("finished loading");

		System.out.println("Training neural network, wait until it finishes");
		an.build();
		an.train(100000);
		System.out.println("Finished training");
		an.save();
		System.out.println("Testing");
		an.testNet("D:/work/bees.txt","D:/train");
		an.accuracy();


	}
}

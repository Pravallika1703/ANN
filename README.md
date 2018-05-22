# ANN
A neural network to classify bees and no-bees images
# Prerequisites:
Prerequisites:
# Steps:
2)	“ANN.java” contains all the necessary code to build and train ANN using back propagation algorithm.
3)	Used one hidden layer with 200 neurons, and learning rate and no added bias.
4)	ANN.java 7 important  functions which are explained below.
-	loadTrainData(): This functions reads bees and no-bees training image data from the folder “D:/train/single_bee_train” which is coded manually inside the function.
-	build(): It is used to initialize ANN with random weights.
-	train(numIterations): this function is used to train ANN model(adjust weights)  until specified numIterations. In each iteration randomly generated image data will be supplied as input to the ANN model.
-	save(): This method is used to Save the trained ANN model to the specified path.
-	restore():  This method is used to restore the saved ANN model in specified path.
-	testNet(Netpath, dirpath): This method restores the saved ANN model at the specificed “Netpath” and loads images data at specified dirpath. And runs the model on every image inside dirpath folder. To run the model on every image data I have used another local function called “fit(Input)”
-	Accuracy():  This function is used to calculate classification accuracy of the bee images classified as bees and no-bees images classified as no bees.
5)	You can comment out all the functions and execute only build() and testNet() method once the model is trained .It automatically restores the saved model and classifies the images specified in a folder.
6)	Attached sample model file saved on my machine. Which can be passed as input to testNet() method.
7)	Run your ANN.java file and you can see the results on screen.

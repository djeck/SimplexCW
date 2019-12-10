JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	compute/Exemple.java\
	compute/Fft.java\
	compute/Sound.java\
	render/Graphe.java\
	render/WaterFall.java\
	render/ReceiverWindow.java\
	render/Launcher.java

default: jar

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f compute/*.class render/*.class SimplexCW.jar

launch: classes
	java render.Launcher

jar: classes
	jar cmvf MANIFEST.MF SimplexCW.jar render/*.class compute/*.class

launch_jar: jar
	java -jar SimplexCW.jar

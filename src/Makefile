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

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

launch:
	java render.Launcher

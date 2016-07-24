MF = /tmp/pegsManifest

PEGS = pegs.jar
SRCDIR = pegs

JFLAGS = -g
JAVAC = javac -cp /$(SRCDIR):${CLASSPATH}

.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $(JFLAGS) $<

_PEGS_SRC = Cell.java \
	CellView.java \
	ColorScheme.java \
	Move.java \
	MovesLeftItem.java \
	MovesLeftKeyAdapter.java \
	Pegs.java \
	PegsGlassPane.java

PEGS_SRC = $(_PEGS_SRC:%=$(SRCDIR)/%)

PEGS_CLASSES = $(PEGS_SRC:.java=.class)

$(PEGS):	$(PEGS_SRC) $(PEGS_CLASSES)
	rm -f $(MF)
	echo "Main-Class: $(SRCDIR)/Pegs" > $(MF)
	jar cmf $(MF) $@ $(SRCDIR)/*.class
	rm -f $(MF)

clean:
	rm -f $(PEGS) $(SRCDIR)/*.class

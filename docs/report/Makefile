# Construit le PDF du rapport LaTex

LATEX_BUILDER=pdflatex --shell-escape
BUILD_FLAGS=-interaction nonstopmode

all: cleanAll buildTitle build show

buildTitle : title.tex
	$(LATEX_BUILDER) $(BUILD_FLAGS) $< #> /dev/null
	$(LATEX_BUILDER) $(BUILD_FLAGS) $< #> /dev/null

build: rapport.tex buildTitle
	$(LATEX_BUILDER) $(BUILD_FLAGS) $< #> /dev/null
	$(LATEX_BUILDER) $(BUILD_FLAGS) $< #> /dev/null

show: rapport.pdf
	if test "${DESKTOP_SESSION}" = "kde-plasma"; then \
		okular $< &> /dev/null & \
	else \
		evince $< &> /dev/null & \
	fi

clean:
	rm -f *.toc *.log *.aux *.tdo

cleanAll: clean
	rm -f *.pdf

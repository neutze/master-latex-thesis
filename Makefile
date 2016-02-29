FILE := main
OUT  := build
FILEF := flow
OUT2  := build2

pdf:
	# Also see .latexmkrc
	rm -rf $(OUT)/*
	/Library/TeX/texbin/latexmk -outdir=$(OUT) -pdf $(FILE)
	rm -rf $(filter-out $(OUT)/$(FILE).pdf, $(wildcard $(OUT)/*))
	cp $(OUT)/$(FILE).pdf ~/Desktop/masterarbeit.pdf
	rm -rf $(OUT2)/*
	/Library/TeX/texbin/latexmk -outdir=$(OUT2) -pdf $(FILEF)
	rm -rf $(filter-out $(OUT2)/$(FILEF).pdf, $(wildcard $(OUT2)/*))
	cp $(OUT2)/$(FILEF).pdf ~/Desktop/flow.pdf

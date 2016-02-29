FILE := main
OUT  := build

pdf:
	# Also see .latexmkrc
	rm -rf build/*
	/Library/TeX/texbin/latexmk -outdir=$(OUT) -pdf $(FILE)
	rm -rf $(filter-out $(OUT)/$(FILE).pdf, $(wildcard $(OUT)/*))
	cp $(OUT)/$(FILE).pdf ~/Desktop/masterarbeit.pdf

FILE2 := flow
OUT2  := build-flow

pdf:
	# Also see .latexmkrc
	rm -rf build-flow/*
	/Library/TeX/texbin/latexmk -outdir=$(OUT2) -pdf $(FILE2)
	rm -rf $(filter-out $(OUT2)/$(FILE2).pdf, $(wildcard $(OUT2)/*))
	cp $(OUT2)/$(FILE2).pdf ~/Desktop/masterarbeit-flow.pdf
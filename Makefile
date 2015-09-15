FILE := main
OUT  := build

pdf:
	# Also see .latexmkrc
	rm -rf build/*
	/Library/TeX/texbin/latexmk -outdir=$(OUT) -pdf $(FILE)
	rm -rf $(filter-out $(OUT)/$(FILE).pdf, $(wildcard $(OUT)/*))

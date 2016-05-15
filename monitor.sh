inotifywait -mr ~/Pictures/hogwarts -e close_write |
	while read path action file; do
		echo "Found $path$file"
		if [[ $path == *.* ]] 
		then
			echo "Not Processing $path$file"
		else
			if [[ $file == DSC* ]]
			then
				echo "Processing $path$file"
				convert $path$file -resize 50% ~/Pictures/converted/$file
				java -cp bin/ com.printer.Printer ~/Pictures/converted/$file
			fi
		fi
	done

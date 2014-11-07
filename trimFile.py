from __future__ import division
import sys, re, math

if (__name__ == "__main__"):
	if (len(sys.argv) != 4):
		print('Usage trimFile.py <infile> <outfile> <rows>')
		sys.exit(1)

	fileName = sys.argv[1]
	outFileName = sys.argv[2]
	count = int(sys.argv[3])

	file = open(fileName, 'r')
	out = open(outFileName, 'w')
	read = 0
	while (read < count):
		line = file.readline()
		if("row" in line):
			out.write(line)
			read += 1
			if (read % (count//50) == 0):
				print(str(read/count*100) + "% completed.")
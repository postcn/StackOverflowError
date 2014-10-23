import sys
import re
from pyspark import SparkContext

if __name__ == "__main__":
	if (len(sys.argv) != 3):
		print >> sys.stderr, "Usage: wordCount <input> <output>"
		exit(-1)
	sc = SparkContext(appName="PythonWordCount")
	lines = sc.textFile(sys.argv[1], 1)
	pattern = re.compile('[\W_]+')
	counts = lines.flatMap(lambda line: line.split(" ")) \
		.map(lambda word: (pattern.sub('',word.upper().encode('ascii')), 1)) \
		.reduceByKey(lambda a, b: a + b)

	counts.saveAsTextFile(sys.argv[2])

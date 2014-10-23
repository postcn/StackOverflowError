import sys
from pyspark import SparkContext

sc = SparkContext("local", "Max Temperature App")
lines = sc.textFile(sys.argv[1], 1)

def isGoodQuality(s):
	if s[92:93] in ["0","1","4","5","9"] and s[88:92] not in "9999":
		return True
	return False
	
def findTemp(s):
	if s[87] in "+":
		return int(s[88:92])
	else:
		return int(s[87:92])
		
def findYear(s):
	return int(s[15:19])

filteredLines = lines.filter(lambda s: isGoodQuality(s))

maxTemps = filteredLines.map(lambda record: (findYear(record), findTemp(record))) \
		.reduceByKey(lambda a, b: max(a,b))

maxTemps.saveAsTextFile(sys.argv[2])
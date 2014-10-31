import sys
from pyspark import SparkContext
from random import random

def calculate_pi(sample_count):
    sc = SparkContext(appName="PiPy")
    samples = float(sc.parallelize(xrange(sample_count)).map(sample).reduce(lambda x, y: x + y))
    print "\nPi is approximately", 4 * (samples / sample_count)

def sample(n):
    return random()**2 + random()**2 <= 1
    
if __name__ == "__main__":
    if len(sys.argv) != 2:
        print "Usage: python pi.py <number of samples>"
        exit(-1)
    sample_count = sys.argv[1]
    try:
        sample_count = int(sample_count)
    except ValueError:
        print "Usage: python pi.py <number of samples>"
        exit(-1)
    calculate_pi(sample_count)

import numpy as np
import matplotlib.pyplot as plt
#%matplotlib inline
import pandas as pd
import chart_studio.plotly as py
import plotly.graph_objs as go
df = pd.read_csv(r"output.csv",parse_dates=['Dates:'],dayfirst=True)
df.head()
df.plot.line(x='Dates:', y='Deaths', figsize=(8,6))


df.savefig("coviddata")
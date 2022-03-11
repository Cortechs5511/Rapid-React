import streamlit as st
import pandas as pd

st.title('Competition Scouting')

uploader = st.empty()

data = uploader.file_uploader("Choose a file: ")

if(data is not None):
    data = pd.read_csv(data, encoding = "utf-8");
    uploader.empty()

try:
    data = data.drop(["Timestamp", "Name", "Qual #"], axis = 1)
    # teams = data["Team #"].to_list()
    # data = data.set_index("Team #")
except:
    pass

if(data is not None):
    teamNum = st.selectbox("Select Team #", data)
    teamData = data.loc[(data['Team #'] == teamNum)]
    st.markdown("Points scored: " + str(teamData['# of upper goal scored'].mean()))

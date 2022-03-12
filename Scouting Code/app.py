import streamlit as st
import pandas as pd
import numpy as np

st.title('Competition Scouting')

uploader = st.empty()

data = uploader.file_uploader("Choose a file: ")

if(data is not None):
    data = pd.read_csv(data, encoding = "utf-8");
    uploader.empty()

try:
    data = data.drop(["Timestamp", "Name", "Qual"], axis = 1)
except:
    pass

if(data is not None):
    teamNum = st.selectbox("Select Team #", data['Team'].unique())
    teamData = data.loc[(data['Team'] == teamNum)]
    dataContainer = st.empty()
    with dataContainer.container():
        st.markdown("Most Frequent Starting Direction: " + teamData["Starting Direction"].value_counts().idxmax())
        st.markdown("Can Leave Tarmac: " + teamData["Tarmac"].value_counts().idxmax())
        st.markdown("Auto Cargo Capabilities: " + ("Upper" if teamData["Auto Upper"].any() and not teamData["Auto Lower"].any()
        else "Lower" if teamData["Auto Lower"].any() and not teamData["Auto Upper"].any()
        else "Upper & Lower" if teamData["Auto Upper"].any() and teamData["Auto Lower"].any()
        else "None"))
        st.markdown("Mean Auto Cargo Points: " + str(2*teamData["Auto Lower"].mean() + 4*teamData["Auto Upper"].mean()))
        st.markdown("Tele-Op Cargo Capabilities: " + ("Upper" if teamData["Upper"].any() and not teamData["Lower"].any()
        else "Lower" if teamData["Lower"].any() and not teamData["Upper"].any()
        else "Upper & Lower" if teamData["Upper"].any() and teamData["Lower"].any()
        else "None"))
        st.markdown("Mean Tele-Op Cargo Points: " + str(1*teamData["Lower"].mean() + 2*teamData["Upper"].mean()))
        st.markdown("Climb: " + ("None" if teamData['Climb Bool'].str.contains('Yes').sum()==0 else ", ".join(teamData['Climb'].unique().tolist())))
        if teamData['Climb Bool'].str.contains('Yes').sum()!=0:
            st.markdown("Climb Time: "+ str(teamData['Climb Time'].mean()))
        st.markdown("Broken/Not Working: " + str(teamData['Break'].str.contains('Yes').sum()) + " Times")
        st.markdown("Average Points: " + str(teamData['Points'].mean()))
        st.markdown("Average Points (Excluding 0): " + str(teamData['Points'][teamData['Points'] != 0].mean()))

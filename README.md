# fly-project
sentiments analysis web application. 
a multi-user web platform that aims to identify users' feelings about various current topics based on the polarity of opinions (positive, negative, neutral) from data source micro blogs and tweets. In fact Microblogs have opinions published by the members on a local forum associated with our platform, whereas tweets are collected from the social network Twitter.

In order to extract the polarity of the feelings from these data we have integrated 3 tools to search for opinions, the first is a java implementation of the Naive Bayes algorithm, the second is in the form of a service in line called Gate Cloud API that communicates via web service and thirdly we used the NLTK python platform with its associated dedicated libraries for analysis of feelings.


from nltk.sentiment.vader import SentimentIntensityAnalyzer
from flask import Flask
   
app = Flask(__name__)

@app.route('/<content>')
def getAnalyseByNltk( content ):
 sid = SentimentIntensityAnalyzer()

 
 ss = sid.polarity_scores(content)
 sent=max(ss['neg'],ss['pos'],ss['neu'])

 if sent==ss['neg']:      
  return "negative";
   
 elif sent==ss['pos']:      
  return "positive";
  
 else  :     
  return "neutral"; 
 

'''@app.route('/<user_id>/<username>')
def show(user_id, username='Anonymous'):
    return user_id + ':' + username'''
# Now you can call getAnalyseByNltk function
#getAnalyseByNltk(content="I want to travel")


if __name__ == '__main__':
    app.run(debug=True,port=8000)



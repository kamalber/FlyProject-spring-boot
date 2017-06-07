from flask import request
from flask import Flask, jsonify
from nltk.sentiment.vader import SentimentIntensityAnalyzer

app = Flask(__name__)

'''array = '{"sentences": ["apple", "banana", "orange"]}'''

@app.route('/sentiments', methods=['POST'])
def create_task():  
    tasks = []      
    for sentence in request.json['sentences']  :
      sid = SentimentIntensityAnalyzer()    
      polarity ="none"
      ss = sid.polarity_scores(sentence)
      sent=max(ss['neg'],ss['pos'],ss['neu'])
 
      if sent==ss['neg']:      
        polarity ="negative";
   
      elif sent==ss['pos']:      
         polarity ="positive";
  
      else  :     
        polarity ="neutral";  
           
      task = {'sentence': sentence,'polarity':polarity }
               
        
        
         
      tasks.append(task)
    return jsonify({'tasks': tasks}), 200
    
if __name__ == '__main__':
    app.run(debug=True)   

## AWS Search Volume
It is a dockerized application that calculates AWS search volumes.
All tests are implemented.

### Built with
* JDK 11
* Spring boot 2

### a. What assumptions did you make?
For each letter, AWS service gets the suggested words sorted by popularity.
### b. How does your algorithm work?
Assume that 'iphone' is out input for the API;
Keyword will be divided into substring, and it will be simulated that user writes letter by letter.
For instance 
* keyword -> `i`
* search amazon -> `ip`
* search amazon -> `iph`

Each keyword, AWS will return a list of suggested list. In our condition it will return 6 list since 'iphone' has 6 character, and let say for each list has 10 suggestions. So we will have 60 suggested word in total.
API will check if word contains our word (iphone) if has then;
100 / 60 point will be added to score.
### c. Do you think the (​*hint​) that we gave you earlier is correct and if so - why?
As I mentioned earlier, it returns mox 10 words sorted by popularity 
### d. How precise do you think your outcome is and why?
We can not say it is 100% accurate, but we can say it is consistent.
Accuracy is also high, let me explain why.
As you know playstation 5 has released, and it is one of the most popular game console in the word.
I expect that ps5 is more popular than ps4, and ps4 is more popular than ps3. Here is the result:
* ps3 -> 33 point
* ps4 -> 47 point
* ps4 -> 57 point

### How to start?
```sh
run init.sh
```
Please make sure that Maven is installed on your computer. Service will start on port 8080.

[Swagger](http://localhost:8080/swagger-ui.html#/)
# AchievementBot

```
POST /achieve 
request:
{
    "event": "NAME",  // name of the achievement
    "type": "BASIC",  // BASIC, DAILY, ANNIVERSARY (always in capital letters)
    "key": ""         // known key of the achievement
}

response: 
{
  "key": ""           // updated key of the achievement
}
```

```
POST /achievements 
request:
[
  ""                  // list of known keys of the earned achievements
]

response: 
  tbd                 // returns badges
```

```
GET /types
response
[
    "BASIC",          // always updates count when called
    "DAILY",          // only updates count once per day but resets to 1 if not daily updated
    "ANNIVERSARY"     // calculates if there is an anniversary (ONLY useable in /achievements)
]
```

# glossary-project


Objective:

Create a service that allows users to manage a glossary or dictionary (like UrbanDictionary).
This microservice must use the Definition service.
This microservice must have adequate unit tests.
This microservice must include exception handling.


Infrastructure (Supplied)
You will be supplied the following services to help complete your project. Each service includes a README file with instructions for running the service and documentation of the API:

Eureka Service Registry
Definition Service


Glossary Service Requirements (You Implement)
You are required to implement two endpoints.

Your service will allow an end user to get a list of definitions for a given term.
Your service will allow an end user to add a new definition, applying the following business rule:


The definition must be family friendly. It cannot contain any of the following words (case insensitive), however these words may exist within other words

darn
heck (however, heckler and checkmate are allowed, for example)
drat (however, dehydrated is allowed, for example)
jerk
butt (however, buttress is allowed, for example)




API

Get List of Definitions by Term


URL: /glossary/term/{term}


HTTP Method: GET


RequestBody: None


ResponseBody: Definition list (JSON - see below)
[
    {
        "id": 1007,
        "term": "I'm dead",
        "definition": "I thought that was very funny"
    },
    {
        "id": 1008,
        "term": "I'm dead",
        "definition": "I am no longer among the living; I am deceeased; (seldom truthfully said)"
    }
]



Add new definition

URL: /glossary
HTTP Method: POST
RequestBody: Definition object without id
{
    "term": "literally",
    "definition": "figuratively"
}

ResponseBody: Definition object with id
{
    "id": 2,
    "term": "literally",
    "definition": "figuratively"
}  
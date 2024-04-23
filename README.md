### MCA Backend

This is my solution to the proposed technical test.

As I told Andrea in an unresponded email, I'm not sure about what's expected on it. I looked at the data model that comes in the SQL and it doesn't have much to do with what the frontend team is asking for according to the statement. I don't see any relation between video games (to get all the items of a saga) or anything related to the relevance of a game within a saga. Also, the `SagaControllerTest` doesn't comply with the specified yamls specification either, nor do I see a way to return the expected data according to what I see in the `schema.sql` and `data.sql` files.

So I don't know if there is something I didn't understand, if I have to modify the database to meet the specification or talk to the frontend team to tell them that they are asking for something that is currently not covered by our backends 😅.

## Running the application

The application is containerized in [Docker](Dockerfile) and the best way to run it is with `docker-compose`. The [docker-compose.yml](docker-compose.yml) has been modified for this purpose:

```bash
docker-compose up -f docker-compose.yml
```

**IMPORTANT NOTE**: My stack is a consists of a M2 Pro chip so I'm using an ARM image. x86 chipsets may need to change the base image of the container (line 12 of the Dockerfile)

Apart from adding the backend, I modified the kafka version to be able to do healtcheck of the services and added:
- healtcheck
- services dependencies
- dependency conditions


## Decisions taken

The decision I took was stick to the statement and add some changes to the database:
- Create a `SAGA_RELATIONSHIP` to relate 2 sagas
- Create two new attributes in the `VIDEOGAME` table:
  - `SAGA_ID`. I assume a videogame belongs to one and only one saga
  - `RELEVANCE` of the videogame within the saga
- Create a view called `VIDEO_GAME_VIEW` to make the query needed for the `/game/{gameId}/saga` endpoint.

I also modified the `VideoGameEvent` to make it a bit more meaningful. Assuming a `VIDEOGAME` had a 1:1 relationship with a `STOCK`, it made more sense for the event to have the videogame id instead of the stock id. Obviously we lose track of the history of the stock and that could be an improvement for this exercise.

## Design

Tried to do a DDD-ish approach, but the business logic here is relatively poor and, apart from the stock update, what we have most are two queries.

But everything goes around the `VideoGame` aggregate, and we only have one domain repository. When a `VideoGame` needs to change its stock we retrieve it, do the update and save the VideoGame.

Apart from that, queries are done separately and are more infrastructure-based

### Tests

You'll find unit tests of almost everything and controller tests where services are mocked.

### Logging

I made use of lombok's logging annotations, and some logging has been added when needed.

## Cache

Cache hasn't been implemented due to lack of time and the uncertainity of going in the right direction due to my unanswered email. But I'd have added a Redis instance in the composer file and added spring capabilities to easily integrate with it. Despite being a possible additional point of failure, by using Redis we can have a shared cache in case we are in an environment where multiple instances of the backend are up and running and our clients communicate with a load balancer. 

For this exmaple I'd have a long lived cache (24 hours?) for every endpoint and would not enable cache eviction anywhere. Why not? Despite the `VideoGameEventProcessor` modifies our `VideoGame` aggregate, it doesn't alter any of our queries so there's no need to redo anything.


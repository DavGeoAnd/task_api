package com.davgeoand.api.task_api.repository.taskData;

import com.arangodb.ArangoCursor;
import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.davgeoand.api.task_api.model.base.TaskConnection;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskConnectionRepository extends ArangoRepository<TaskConnection, String> {
    @Query("FOR tc IN taskConnections FILTER tc._class LIKE \"%OwnTask\" RETURN tc")
    ArangoCursor<TaskConnection> findAllOwnTasks();

    @Query("FOR tc IN taskConnections FILTER tc._class LIKE \"%ShareTask\" RETURN tc")
    ArangoCursor<TaskConnection> findAllShareTasks();

    @Query("FOR tc IN taskConnections FILTER tc._from == @userId RETURN tc")
    ArangoCursor<TaskConnection> findAllForUser(@Param("userId") String userId);
}
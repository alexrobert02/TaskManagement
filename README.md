# TaskManagement

## Business Requirements
- **User Management**: Enable administrators to create, update, and delete user profiles.
- **Task Creation and Assignment**: Allow users to create tasks and assign them.
- **Project Management**: Provide functionality to create, update, and delete projects, and assign users to projects.
- **Commenting System**: Facilitate commenting on tasks for better collaboration.
- **Search Functionality**: Enable searching for projects, tasks, and comments based on various criteria.
- **Task Prioritization**: Allow users to set priority levels for tasks (e.g., High, Medium, Low).
- **Status Tracking**: Provide task status updates, such as "Not Started," "In Progress," and "Completed."
- **Due Date Reminders**: Notify users about upcoming task deadlines.
- **User Role Management**: Define roles (e.g., Admin, User) and restrict features based on roles.
- **API Documentation**: Provide a detailed API documentation for seamless integration with other systems.

## MVP Features

### Feature: **User Management**
**Description**: Manage users within the system. This feature allows administrators and relevant users to add, retrieve, and delete users as needed.

- **Endpoints**:
    - `POST /user`: Create a new user.
    - `GET /user`: Retrieve all users.
    - `GET /user/{id}`: Retrieve user by ID.
    - `DELETE /user/{id}`: Delete a user by ID.
- **Controllers**: UserController

---

### Feature: **Task Creation and Assignment**
**Description**: Users can create tasks, assign them to team members, and set their priorities and due dates. This feature ensures effective task delegation and tracking.

- **Endpoints**:
    - `POST /task`: Create a new task.
    - `GET /task/{id}`: Retrieve task by ID.
    - `GET /task/search`: Search tasks by filters such as status, priority, and assigned user.
    - `GET /task/due-dates/upcoming/{userId}`: Get tasks with upcoming due dates for a specified user.
    - `DELETE /task/{id}`: Delete a task by ID.
    - `PUT /task/{id}`: Update task details.
- **Controllers**: TaskController

---

### Feature: **Project Management**
**Description**: Users can create and manage projects, assign team members, and monitor project progress. This feature provides a centralized view of project activities.

- **Endpoints**:
    - `POST /api/projects`: Create a new project.
    - `GET /api/projects/{id}`: Retrieve project by ID.
    - `GET /api/projects/search`: Search for projects by name or description.
    - `DELETE /api/projects/{id}`: Delete a project by ID.
    - `PATCH /api/projects/{projectId}/assign-users`: Assign users to a project.
- **Controllers**: ProjectController

---

### Feature: **Commenting System**
**Description**: Facilitate communication among team members by allowing comments on tasks. This feature enhances collaboration and documentation of task-related discussions.

- **Endpoints**:
    - `POST /comment`: Create a new comment.
    - `PUT /comment/{id}`: Update the content of a comment.
    - `GET /comment`: Retrieve all comments.
- **Controllers**: CommentController

---

### Feature: **Search Functionality**
**Description**: Enable users to search for tasks and projects based on filters like status, priority, and assigned user. This feature helps users quickly find relevant information.

- **Endpoints**:
    - `GET /task/search`: Search for tasks based on status, priority, and assigned user.
    - `GET /api/projects/search`: Search for projects by name or description.
- **Controllers**: TaskController, ProjectController

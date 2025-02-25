# Amadinho User Guide

Hello. I am Amadinho, Manchester United's most useful player this season (24/25). However, I am now out for 
the rest of the season with an injury. So, I have decided to remain useful by creating a taskbot for you! It can 
help you to keep track of your tasks, and help you get more organized. Enjoy!

## Features
### Adding a Todo: `todo`
Format: `todo <description>`  
Adds a Todo into the list.  
*Todo Tasks are for tasks with no deadline or duration.*
> Example: `todo go for training`

### Adding a Deadline: `deadline`
Format: `deadline <description> /by <yyyy-MM-dd HHmm>`  
Adds a Deadline into the list.  
*Deadline Tasks are for tasks with a deadline.*
> Example: `deadline score a hat trick /by 2025-05-25 1700`

### Adding an Event: `event`
Format: `event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`  
Adds an Event into the list.  
*Event Tasks are for tasks with a start and end time.*
> Example: `event manchester derby /from 2025-04-05 1400 /to 2025-04-05 1600`

### Marking: `mark`
Format: `mark <integer>`  
Marks the Task in that position in the list as complete.
> Example: `mark 3`

### Unmarking: `unmark`
Format: `unmark <integer>`  
Unmarks the Task in that position in the list, which is the same as marking the Task as incomplete.
> Example: `unmark 1`

### Searching Tasks: `find`
Format: `find <description>`  
Searches for Tasks with the given description.
> Example: `find this`

### Adding a Deadline: `delete`
Format: `delete <integer>`  
Deletes the Task in that position in the list.
> Example: `delete 2`

### Show all Tasks: `list`
Format: `list`  
Shows all Tasks present in List.

### Exit the taskbot: `bye`
Format: `bye`  
Closes the chatbot.

### Saving data
Data in the list is saved automatically after any command that changes the list. There is no need to save manually. :D

### Editing data
Data in the list is saved as a text file in `[JAR file location]/data/amadinho.txt`. 

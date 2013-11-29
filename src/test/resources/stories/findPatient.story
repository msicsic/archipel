Scenario: search a patient

Given a repository with a patient named "Anderson"
Given a repository with a patient named "Amory"
When a search is made on name with "and"
Then patient Anderson is found
When a search is made on name with "tot"
Then no patient is found
When a search is made on name with "a"
Then Anderson and Amory are found
Scenario: create a medical center

Given configured MedicalCenterInfo and Division
When the user register a new MedicalCenter
Then the created MedicalCenter is available in the EventStore
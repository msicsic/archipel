Scenario: create a medical center
When the user register a new MedicalCenter with name center1, type CHU, ident CTR1
Then the new MedicalCenter has a default Division with one Sector of type MED
Then the new MedicalCenter has an Id
Then the new MedicalCenter is available in the EventStore

Scenario: update additional infos
Given a MedicalCenter
When the user update the additional infos
Then the MedicalCenter contains the modified data
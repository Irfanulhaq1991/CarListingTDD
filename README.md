### Details & Description
 - Architecture : MVVM with Unidirectional data flow. It could be called MVI too.
 - State Retaining: Android ViewModel
 - Publish Subscribe: Livedata
 - UI State Management: Data class
 - Dependency Injection: Koin
 - UI Binding: Android data binding
 - Guidelines: DRY, YAGNI, SOlID
 - Pattern: Repository, Clean Architecture, Singleton
 - Searching Algorithm: Kotlin Collection 
 - Database: None
 - Screen Rotation: Supported
 - Development Strategy :
     - Outside-In TDD
     - Domain driven development
 - Test pyramid:
     - Small tests : 45
     - Medium tests: 4
     - Large tests: 0
 - Testing Tools
     - custom coroutine rule
     - Junit

     - MockK
     - Truth assertion
 - JVM Test: 49, including contract tests
 - Instrument Test or Ui Test: 0
 
#### Note: 
Please add the API URL and key in the local.properties gradel file with BAS_URL,URL_TOKEN before building the project. For example

  - BAS_URL =http://abc.com/v1/car-types/
  - URL_TOKEN =coding
     

# status-messages backlog
===================================
----
## *(2014.02.01)*
- StatusMessage's first argument needs to be refactored from "code" to "message". 
	*	If *message* is a i18n code 
then it should be expressed like "{code}" and then it will be resolved with a MessageSource.
	*	If *message* is a plain message then it should be treated like a String.format() pattern.
- Receive "resolvable arguments". if an argument is an instance of *Resolvable* then it will be 
processed like a i18 code (possibly with its own arguments).

----
## *(2013.07.19)*

-	Define the abstraction levels

	*	StatusMessagesMethdoArgumentResolver:
			Spring-based, resolve @Controller handler parameter of type `StatusMessages`
	*	StatusMessagePublisher:
			Isolated. Publish messages of type `StatusMessage` in the underlying flash scope abstraction
	*	StatusMessageFlashScopeHolder:
			Isolated. It holds the interactions with FlashScope knowing about `StatusMessage`
			`StatusMessageFlashScopeHolder#mergeFromPreviousRequest()`
			`StatusMessageFlashScopeHolder#get()`
	*	FlashScopeAccessor:
			Should be isolated. Abstraction layer that interacts with the particular flash scope implementation.
	*	SpringFlashScopeAccessor:
			Spring-based `FlashScopeAccessor` implementation?
			
**WIP:** `StatusMessagesPublisher`

---- 
## *(2013.07.12)*

- Publisher:
	[x]	Build `StatusMessagesPublisher` with a `HttpServletRequest` ~ `StatusMessagePublisher.fromCurrentRequest()` or `StatusMessagePublisher.fromRequest(HttpServletRequest request)`
	[x]	Use of `FlashScopeAccessor<StatusMessages>` to publish `StatusMessages` in the flash scope
	
- Argument Resolver:
	[x]	Use of publisher to work with flash scope ?
	[x]	Create a new `StatusMessages`, store it in flash scope & return it to the handler
	[x]	Inspect the input flash scope for a previous `StatusMessages` (ie. from a previous redirect) and then do `new StatusMessages(previousStatusMessages)`

- Fixtures:
	[x]	Extract a new fixture to manage `HttpServletRequest` and `FlashScopeAcessor` -> `MockedSpringHttpServletRequest`
	
----
	
	
	
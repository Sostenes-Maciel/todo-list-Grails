package todolistbackend

class UrlMappings {

    static mappings = {
        "/api/tarefas"(resources: 'tarefa', excludes: ['create', 'edit'])

        "/"(controller: 'application', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
package todolistbackend

class TarefaController {

    static responseFormats = ['json']

    static allowedMethods = [
            index : 'GET',
            show  : 'GET',
            save  : 'POST',
            update: 'PUT',
            patch : 'PATCH',
            delete: 'DELETE'
    ]

    TarefaService tarefaService

    def index() {
        respond tarefaService.listar()
    }

    def show(Long id) {
        Tarefa tarefa = tarefaService.buscar(id)

        if (!tarefa) {
            respond([erro: 'Tarefa não encontrada.'], status: 404)
            return
        }

        respond tarefa
    }

    def save() {
        Tarefa tarefa = new Tarefa()

        if (!preencherTarefa(tarefa)) {
            return
        }

        if (!tarefa.validate()) {
            responderErros(tarefa)
            return
        }

        Tarefa salva = tarefaService.salvar(tarefa)

        if (!salva) {
            responderErros(tarefa)
            return
        }

        respond salva, [status: 201]
    }

    def update(Long id) {
        Tarefa tarefa = tarefaService.buscar(id)

        if (!tarefa) {
            respond([erro: 'Tarefa não encontrada.'], status: 404)
            return
        }

        if (!preencherTarefa(tarefa, true)) {
            return
        }

        if (!tarefa.validate()) {
            responderErros(tarefa)
            return
        }

        Tarefa atualizada = tarefaService.salvar(tarefa)

        if (!atualizada) {
            responderErros(tarefa)
            return
        }

        respond atualizada
    }

    def patch(Long id) {
        Tarefa tarefa = tarefaService.buscar(id)

        if (!tarefa) {
            respond([erro: 'Tarefa não encontrada.'], status: 404)
            return
        }

        if (!preencherTarefa(tarefa)) {
            return
        }

        if (!tarefa.validate()) {
            responderErros(tarefa)
            return
        }

        Tarefa atualizada = tarefaService.salvar(tarefa)

        if (!atualizada) {
            responderErros(tarefa)
            return
        }

        respond atualizada
    }

    def delete(Long id) {
        Tarefa tarefa = tarefaService.buscar(id)

        if (!tarefa) {
            respond([erro: 'Tarefa não encontrada.'], status: 404)
            return
        }

        tarefaService.excluir(tarefa)

        render status: 204
    }

    private boolean preencherTarefa(
            Tarefa tarefa,
            boolean substituicaoCompleta = false
    ) {
        Map dados = new LinkedHashMap(request.JSON as Map)

        boolean possuiCategoriaId = dados.containsKey('categoriaId')
        def categoriaId = dados.remove('categoriaId')

        if (substituicaoCompleta) {
            tarefa.nome = null
            tarefa.descricao = null
            tarefa.dataTermino = null
            tarefa.prioridade = null
            tarefa.status = null
            tarefa.horarioAlarme = null
            tarefa.categoria = null
        }

        bindData(tarefa, dados, [
                include: [
                        'nome',
                        'descricao',
                        'dataTermino',
                        'prioridade',
                        'status',
                        'horarioAlarme'
                ]
        ])

        if (possuiCategoriaId || substituicaoCompleta) {
            if (categoriaId == null) {
                tarefa.categoria = null
            } else {
                Long idCategoria

                try {
                    idCategoria = categoriaId.toString().toLong()
                } catch (NumberFormatException e) {
                    respond([erro: 'Identificador de categoria inválido.'], status: 422)
                    return false
                }

                Categoria categoria = tarefaService.buscarCategoria(idCategoria)

                if (!categoria) {
                    respond([erro: 'Categoria não encontrada.'], status: 422)
                    return false
                }

                tarefa.categoria = categoria
            }
        }

        return true
    }

    private void responderErros(Tarefa tarefa) {
        respond([
                erros: tarefa.errors.fieldErrors.collect {
                    [campo: it.field, mensagem: it.defaultMessage]
                }
        ], status: 422)
    }
}
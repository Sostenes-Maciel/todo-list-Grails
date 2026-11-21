package todolistbackend

enum Status {

    TODO("A Fazer"),
    DOING("Em Andamento"),
    DONE("Concluído")

    final String descricao

    Status(String descricao) {
        this.descricao = descricao
    }

    Status avancar() {
        switch (this) {
            case TODO:
                return DOING

            case DOING:
                return DONE

            default:
                return this
        }
    }
}
package todolistbackend

import spock.lang.Specification

class StatusSpec extends Specification {

    void "TODO deve avancar para DOING"() {
        expect:
        Status.TODO.avancar() == Status.DOING
    }

    void "DOING deve avancar para DONE"() {
        expect:
        Status.DOING.avancar() == Status.DONE
    }

    void "DONE deve permanecer DONE"() {
        expect:
        Status.DONE.avancar() == Status.DONE
    }
}
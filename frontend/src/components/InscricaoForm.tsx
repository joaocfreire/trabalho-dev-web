import type { Aluno } from "../interfaces/Aluno";
import type { Disciplina } from "../interfaces/Disciplina";
import type { Turma } from "../interfaces/Turma";
import useAlunoStore from "../store/AlunoStore";
import useTurmaStore from "../store/TurmaStore";
import { Formatters } from "../util/Formatters";
import useRecuperarInscricoes from "../hooks/useRecuperarInscricoes";
import useRecuperarTurmas from "../hooks/useRecuperarTurmas";
import type { Inscricao } from "../interfaces/Inscricao";
import useCadastrarInscricao from "../hooks/useCadastrarInscricao";

const InscricaoForm = () => {
const { 
    data: turmas, 
    error: erroRecuperarTurmas 
} = useRecuperarTurmas();

const {
    data: inscricoes,
    isPending: recuperandoAlunos,
    error: erroRecuperarAlunos,
  } = useRecuperarInscricoes();
  
const getTurma = (id:number)=>{
    for(const turma of turmas!){
      if(turma.id == id){
        return turma;
      }
    }
    return null;
}
const getDisciplinasPelasTurmas = (turmas:any)=>{
  let disciplinas:Disciplina[] = [];
  turmas.forEach((turma:Turma)=>{ // para cada turma
    let presente:boolean = false;
    disciplinas.forEach((disciplina:Disciplina)=>{ // verificando se já não foi colocada
      if(disciplina.id == turma.disciplina.id){
        presente = true;
      }
    });
    if(!presente){
      disciplinas.push(turma.disciplina);
    }
  });
  return disciplinas;
}
const getAluno = (id:number)=>{
    for(const inscricao of inscricoes!){
        if(inscricao.aluno.id == id){
            return inscricao.aluno;
        }
    }
    return null;
}

if(erroRecuperarTurmas){
    throw erroRecuperarTurmas;
}
if(erroRecuperarAlunos){
    throw erroRecuperarAlunos;
}
const setTurmaSelecionada = useTurmaStore((s) => s.setTurmaSelecionado);
const turmaSelecionada = useTurmaStore((s) => s.turmaSelecionado);
const setIdTurma = useTurmaStore((s) => s.setIdTurma);
const setAlunoSelecionado = useAlunoStore((s) => s.setAlunoSelecionado);

const setTurmasFiltradas = useTurmaStore((s)=>s.setTurmasFiltradas);
const turmasFiltradas = useTurmaStore((s)=>s.turmasFiltradas);

const alunosFiltrados = useAlunoStore((s)=>s.alunosFiltrados);
const alunoSelecionado = useAlunoStore((s)=>s.alunoSelecionado);

const setAlunosFiltrados = useAlunoStore((s)=>s.setAlunosFiltrados);

const tratarSelecaoTurma = (evento:any)=>{
    const turma = getTurma(evento.target.value);
    
    //filtra a lista de alunos com base na turma
    let novoAlunosFiltrados:Aluno[] = [];
    // horrorosamente ineficiente, mas foi o único jeito que consegui fazer funcionar
    inscricoes!.forEach((inscricao:Inscricao)=>{ // para cada inscrição
      let estaNaTurma:boolean = false;
      // verifico se a inscrição não está na turma atual
      inscricoes!.forEach((inscricaoTurmaAtual:Inscricao)=>{ //novamente, para cada inscrição, porque não consigo recuperar as inscrições na turma
        if(inscricaoTurmaAtual.aluno.id == inscricao.aluno.id && inscricaoTurmaAtual.turma.id == turma!.id){
          estaNaTurma = true;
        }
      });
      let jaColoquei:boolean = false;
      novoAlunosFiltrados.forEach((aluno:Aluno)=>{
        if(inscricao.aluno.id == aluno.id){
          jaColoquei = true;
        }
      });
      if(!estaNaTurma && !jaColoquei){
        novoAlunosFiltrados.push(inscricao.aluno);
      }
     
    });
    
    setAlunosFiltrados(novoAlunosFiltrados);
    setTurmaSelecionada(turma!);
    setIdTurma(turma!.id);
};


const tratarSelecaoDisciplina = (evento:any)=>{
  const disciplinaId = evento.target.value;
  let turmasDois:Turma[] = [];
  if(disciplinaId != -1){
    turmas!.forEach((turma:Turma)=>{
      if(turma.disciplina.id == disciplinaId){
        turmasDois.push(turma);
      }
    });
  }
  console.log(turmasDois);
  setTurmasFiltradas(turmasDois);
  console.log(turmasFiltradas);
};
const tratarSelecaoAluno = (evento:any)=>{
    const aluno = getAluno(evento.target.value);
    setAlunoSelecionado(aluno!);
    
};
  const { mutate: cadastrarInscricao, error: errorCadastrarInscricao } = useCadastrarInscricao();

const disciplinas:Disciplina[] = getDisciplinasPelasTurmas(turmas);
const handleSubmit = ()=>{
  console.log(alunoSelecionado);
  console.log(turmaSelecionada);
  cadastrarInscricao({
    aluno:alunoSelecionado,
    turma:turmaSelecionada as Turma,
    dataHora:"2020-05-01"
  });
}
if(recuperandoAlunos){
  return(<p>Recuperando alunos</p>);
}

  return(
    <>
      <h3>Inscrever um aluno:</h3>
      <h4>Disciplinas: </h4>
      <select onChange={tratarSelecaoDisciplina}className="form-select mb-3" aria-label="Seletor de disciplinas">
        <option value={-1} key={-1}>Selecione uma disciplina</option>
        {disciplinas.map((disciplina: Disciplina) => (
          <option value={disciplina.id} key={disciplina.id}>
            {disciplina.nome}
          </option>
        ))}
      </select>
      <h4>Turmas: </h4>
      <select onChange={tratarSelecaoTurma}className="form-select mb-3" aria-label="Seletor de turmas">
        <option value={-1} key={-1}>Selecione uma turma</option>
        {turmasFiltradas.map((turma) => (
          <option value={turma.id} key={turma.id}>
            {Formatters.getFullTurmaNome(turma as Turma)}
          </option>
        ))}
      </select>
      <h4>Alunos: </h4>
      <select onChange={tratarSelecaoAluno}className="form-select mb-3" aria-label="Seletor de alunos">
        <option value={-1} key={-1}>Selecione um aluno</option>
        {alunosFiltrados.map((aluno:Aluno) => (
          <option value={aluno.id} key={aluno.id}>
            {aluno.nome}
          </option>
        ))}
      </select>
      
      <button onClick={handleSubmit}>Inscrever aluno</button>
    </>
  );
}
export default InscricaoForm
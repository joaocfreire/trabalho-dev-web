import useRecuperarInscricoesPorTurma from "../hooks/useRecuperarInscricoesPorTurma";
import { useEffect, useState} from "react";
import useRecuperarTurmas from "../hooks/useRecuperarTurmas";
import type { Aluno } from "../interfaces/Aluno";
import InscricaoForm from "../components/InscricaoForm";
import useTurmaStore from "../store/TurmaStore";
import TabelaDeInscricoesComAddGrupo from "../components/TabelaDeInscricoesComAddGrupo";
import { Formatters } from "../util/Formatters";
import type { Turma } from "../interfaces/Turma";

const AlunosPorTurmaPage = () => {

const setIdTurma = useTurmaStore((s) => s.setIdTurma);
const idTurma = useTurmaStore((s) => s.idTurma);

  const { 
    data: turmas, 
    error: erroRecuperarTurmas 
  } = useRecuperarTurmas();

  const {
    data: inscricoes,
    isPending: recuperandoAlunos,
    error: errorRecuperarAlunos,
  } = useRecuperarInscricoesPorTurma(String(idTurma));
   //=================PRECISA VIR DEPOIS DE APLICARMOS O IDTURMA PQ USAMOS ESTE AQUI=====================
    const[grupo, setGrupo] = useState(()=>{
    const alunosNoGrupoDaTurma = localStorage.getItem("grupo"+idTurma); //um grupo para cada turma. apenas 1 carregado por vez
    return alunosNoGrupoDaTurma?JSON.parse(alunosNoGrupoDaTurma):[];
  });

  useEffect(()=>{ // método que salva o grupo no localStorage, como um dicionário
    localStorage.setItem("grupo"+idTurma, JSON.stringify(grupo));
  },[grupo]);

  const getGrupo = (id:number)=>{ // precisei desse getGrupo porque o idTurma não atualiza a tempo e ele acaba usando o grupo anterior da mesma forma. Assim funciona, ele grava em grupos independentes
    const alunosNoGrupoDaTurma = localStorage.getItem("grupo"+id); //um grupo para cada turma. apenas 1 carregado por vez
    return alunosNoGrupoDaTurma?JSON.parse(alunosNoGrupoDaTurma):[];
  }

  const adicionarAoGrupo = (aluno:Aluno)=>{
    setGrupo((grupoAnterior:number[])=>{ // grupo vai ser um array de ids de alunos
      grupoAnterior.push(aluno.id!);
      // precisa criar um novo array pq o react só detecta mudança assim
      let novoGrupo:number[] = [];
      grupoAnterior.forEach((elemento:number)=>{
        novoGrupo.push(elemento);
      });
      return novoGrupo;
    });
  }

  const removerDoGrupo = (aluno:Aluno)=>{
    setGrupo((grupoAnterior:number[])=>{ // grupo vai ser um array de ids de alunos
      let novoGrupo:number[] = [];
      grupoAnterior.forEach((elemento:number)=>{
        if(elemento != aluno.id){
          novoGrupo.push(elemento);
        }
      })
      return novoGrupo;
    });
  }
  
  if (errorRecuperarAlunos) throw errorRecuperarAlunos;
  if (erroRecuperarTurmas) throw erroRecuperarTurmas;
  if (recuperandoAlunos) return <p>Recuperando alunos...</p>;
  const tratarSelecao = (evento:any)=>{
      setIdTurma(evento.target.value);
      setGrupo(getGrupo(Number(evento.target.value)));
  }
  
  return (
    <>
    
      <InscricaoForm/>
      <hr/>
      <h3>Inscrições e grupos por turma:</h3>
      <select onChange={tratarSelecao}className="form-select mb-3" aria-label="Seletor de turmas">
        {turmas.map((turma: Turma) => (
          <option value={turma.id} key={turma.id}>
            {Formatters.getFullTurmaNome(turma)}
          </option>
        ))}
      </select>
      <h5>Lista de Alunos na turma</h5>
      <hr className="mt-1" />
      <TabelaDeInscricoesComAddGrupo inscricoes={inscricoes} grupo={grupo} adicionarAoGrupo={adicionarAoGrupo} removerDoGrupo={removerDoGrupo} />
    </>
  );
};
export default AlunosPorTurmaPage;

/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at https://github.com/gunterze/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Agfa Healthcare.
 * Portions created by the Initial Developer are Copyright (C) 2011
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * See @authors listed below
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.dcm4che.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.dcm4che.data.Attributes;
import org.dcm4che.data.Tag;
import org.dcm4che.net.pdu.CommonExtendedNegotiation;
import org.dcm4che.net.pdu.PresentationContext;
import org.dcm4che.net.service.CEchoSCP;
import org.dcm4che.net.service.CFindSCP;
import org.dcm4che.net.service.CGetSCP;
import org.dcm4che.net.service.CMoveSCP;
import org.dcm4che.net.service.CStoreSCP;
import org.dcm4che.net.service.DicomService;
import org.dcm4che.net.service.NActionSCP;
import org.dcm4che.net.service.NCreateSCP;
import org.dcm4che.net.service.NDeleteSCP;
import org.dcm4che.net.service.NEventReportSCU;
import org.dcm4che.net.service.NGetSCP;
import org.dcm4che.net.service.NSetSCP;

/**
 * @author Gunter Zeilinger <gunterze@gmail.com>
 *
 */
class DicomServiceRegistry {

    private final HashSet<String> sopCUIDs = new HashSet<String>();
    private final HashMap<String, CEchoSCP> cechoSCPs =
            new HashMap<String, CEchoSCP>(1);
    private HashMap<String, CStoreSCP> cstoreSCPs;
    private HashMap<String, CGetSCP> cgetSCPs;
    private HashMap<String, CFindSCP> cfindSCPs;
    private HashMap<String, CMoveSCP> cmoveSCPs;
    private HashMap<String, NGetSCP> ngetSCPs;
    private HashMap<String, NSetSCP> nsetSCPs;
    private HashMap<String, NActionSCP> nactionSCPs;
    private HashMap<String, NCreateSCP> ncreateSCPs;
    private HashMap<String, NDeleteSCP> ndeleteSCPs;
    private HashMap<String, NEventReportSCU> neventReportSCUs;

    public synchronized void addDicomService(DicomService service) {
        String[] sopClasses = service.getSopClasses();
        String serviceClass = service.getServiceClass();
        for (String uid : sopClasses)
            sopCUIDs.add(uid);
        if (serviceClass != null)
            sopCUIDs.add(serviceClass);
        if (service instanceof CEchoSCP) {
            for (String uid : sopClasses)
                cechoSCPs.put(uid, (CEchoSCP) service);
            if (serviceClass != null)
                cechoSCPs.put(serviceClass, (CEchoSCP) service);
        }
        if (service instanceof CStoreSCP) {
            if (cstoreSCPs == null)
                cstoreSCPs = new HashMap<String, CStoreSCP>();
            for (String uid : sopClasses)
                cstoreSCPs.put(uid, (CStoreSCP) service);
            if (serviceClass != null)
                cstoreSCPs.put(serviceClass, (CStoreSCP) service);
        }
        if (service instanceof CGetSCP) {
            if (cgetSCPs == null)
                cgetSCPs = new HashMap<String, CGetSCP>();
            for (String uid : sopClasses)
                cgetSCPs.put(uid, (CGetSCP) service);
            if (serviceClass != null)
                cgetSCPs.put(serviceClass, (CGetSCP) service);
        }
        if (service instanceof CFindSCP) {
            if (cfindSCPs == null)
                cfindSCPs = new HashMap<String, CFindSCP>();
            for (String uid : sopClasses)
                cfindSCPs.put(uid, (CFindSCP) service);
            if (serviceClass != null)
                cfindSCPs.put(serviceClass, (CFindSCP) service);
        }
        if (service instanceof CMoveSCP) {
            if (cmoveSCPs == null)
                cmoveSCPs = new HashMap<String, CMoveSCP>();
            for (String uid : sopClasses)
                cmoveSCPs.put(uid, (CMoveSCP) service);
            if (serviceClass != null)
                cmoveSCPs.put(serviceClass, (CMoveSCP) service);
        }
        if (service instanceof NGetSCP) {
            if (ngetSCPs == null)
                ngetSCPs = new HashMap<String, NGetSCP>();
            for (String uid : sopClasses)
                ngetSCPs.put(uid, (NGetSCP) service);
            if (serviceClass != null)
                ngetSCPs.put(serviceClass, (NGetSCP) service);
        }
        if (service instanceof NSetSCP) {
            if (nsetSCPs == null)
                nsetSCPs = new HashMap<String, NSetSCP>();
            for (String uid : sopClasses)
                nsetSCPs.put(uid, (NSetSCP) service);
            if (serviceClass != null)
                nsetSCPs.put(serviceClass, (NSetSCP) service);
        }
        if (service instanceof NCreateSCP) {
            if (ncreateSCPs == null)
                ncreateSCPs = new HashMap<String, NCreateSCP>();
            for (String uid : sopClasses)
                ncreateSCPs.put(uid, (NCreateSCP) service);
            if (serviceClass != null)
                ncreateSCPs.put(serviceClass, (NCreateSCP) service);
        }
        if (service instanceof NActionSCP) {
            if (nactionSCPs == null)
                nactionSCPs = new HashMap<String, NActionSCP>();
            for (String uid : sopClasses)
                nactionSCPs.put(uid, (NActionSCP) service);
            if (serviceClass != null)
                nactionSCPs.put(serviceClass, (NActionSCP) service);
        }
        if (service instanceof NEventReportSCU) {
            if (neventReportSCUs == null)
                neventReportSCUs = new HashMap<String, NEventReportSCU>();
            for (String uid : sopClasses)
                neventReportSCUs.put(uid, (NEventReportSCU) service);
            if (serviceClass != null)
                neventReportSCUs.put(serviceClass, (NEventReportSCU) service);
        }
        if (service instanceof NDeleteSCP) {
            if (ndeleteSCPs == null)
                ndeleteSCPs = new HashMap<String, NDeleteSCP>();
            for (String uid : sopClasses)
                ndeleteSCPs.put(uid, (NDeleteSCP) service);
            if (serviceClass != null)
                ndeleteSCPs.put(serviceClass, (NDeleteSCP) service);
        }
    }

    public synchronized void removeDicomService(DicomService service) {
        String[] sopClasses = service.getSopClasses();
        String serviceClass = service.getServiceClass();
        for (String uid : sopClasses)
            sopCUIDs.remove(uid);
        if (serviceClass != null)
            sopCUIDs.remove(serviceClass);
        if (service instanceof CEchoSCP) {
            for (String uid : sopClasses)
                cechoSCPs.remove(uid);
            if (serviceClass != null)
                cechoSCPs.remove(serviceClass);
        }
        if (service instanceof CStoreSCP) {
            if (cstoreSCPs != null) {
                for (String uid : sopClasses)
                    cstoreSCPs.remove(uid);
                if (serviceClass != null)
                    cstoreSCPs.remove(serviceClass);
            }
        }
        if (service instanceof CGetSCP) {
            if (cgetSCPs != null) {
                for (String uid : sopClasses)
                    cgetSCPs.remove(uid);
                if (serviceClass != null)
                    cgetSCPs.remove(serviceClass);
            }
        }
        if (service instanceof CFindSCP) {
            if (cfindSCPs != null) {
                for (String uid : sopClasses)
                    cfindSCPs.remove(uid);
                if (serviceClass != null)
                    cfindSCPs.remove(serviceClass);
            }
        }
        if (service instanceof CMoveSCP) {
            if (cmoveSCPs != null) {
                for (String uid : sopClasses)
                    cmoveSCPs.remove(uid);
                if (serviceClass != null)
                    cmoveSCPs.remove(serviceClass);
            }
        }
        if (service instanceof NGetSCP) {
            if (ngetSCPs != null) {
                for (String uid : sopClasses)
                    ngetSCPs.remove(uid);
                if (serviceClass != null)
                    ngetSCPs.remove(serviceClass);
            }
        }
        if (service instanceof NSetSCP) {
            if (nsetSCPs != null) {
                for (String uid : sopClasses)
                    nsetSCPs.remove(uid);
                if (serviceClass != null)
                    nsetSCPs.remove(serviceClass);
            }
        }
        if (service instanceof NCreateSCP) {
            if (ncreateSCPs != null) {
                for (String uid : sopClasses)
                    ncreateSCPs.remove(uid);
                if (serviceClass != null)
                    ncreateSCPs.remove(serviceClass);
            }
        }
        if (service instanceof NActionSCP) {
            if (nactionSCPs != null) {
                for (String uid : sopClasses)
                    nactionSCPs.remove(uid);
                if (serviceClass != null)
                    nactionSCPs.remove(serviceClass);
            }
        }
        if (service instanceof NEventReportSCU) {
            if (neventReportSCUs != null) {
                for (String uid : sopClasses)
                    neventReportSCUs.remove(uid);
                if (serviceClass != null)
                    neventReportSCUs.remove(serviceClass);
            }
        }
        if (service instanceof NDeleteSCP) {
            if (ndeleteSCPs != null) {
                for (String uid : sopClasses)
                    ndeleteSCPs.remove(uid);
                if (serviceClass != null)
                    ndeleteSCPs.remove(serviceClass);
            }
        }
    }

    void process(Association as, PresentationContext pc, Attributes cmd,
            PDVInputStream data) throws IOException {
        try {
            final int cmdfield = cmd.getInt(Tag.CommandField, -1);
            if (cmdfield == Commands.C_STORE_RQ) {
                cstore(as, pc, cmd, data);
            } else {
                Attributes dataset = data != null
                        ? data.readDataset(pc.getTransferSyntax())
                        : null;
                switch (cmdfield) {
                case Commands.C_GET_RQ:
                    cget(as, pc, cmd, dataset);
                    break;
                case Commands.C_FIND_RQ:
                    cfind(as, pc, cmd, dataset);
                    break;
                case Commands.C_MOVE_RQ:
                    cmove(as, pc, cmd, dataset);
                    break;
                case Commands.C_ECHO_RQ:
                    cecho(as, pc, cmd, dataset);
                    break;
                case Commands.N_EVENT_REPORT_RQ:
                    neventReport(as, pc, cmd, dataset);
                    break;
                case Commands.N_GET_RQ:
                    nget(as, pc, cmd, dataset);
                    break;
                case Commands.N_SET_RQ:
                    nset(as, pc, cmd, dataset);
                    break;
                case Commands.N_ACTION_RQ:
                    naction(as, pc, cmd, dataset);
                    break;
                case Commands.N_CREATE_RQ:
                    ncreate(as, pc, cmd, dataset);
                    break;
                case Commands.N_DELETE_RQ:
                    ndelete(as, pc, cmd, dataset);
                    break;
                default:
                    throw new DicomServiceException(cmd,
                            Status.UnrecognizedOperation);
                }
            }
        } catch (DicomServiceException e) {
            as.writeDimseRSP(pc, e.getCommand(), e.getDataset());
        }
    }

    private <T> T service(HashMap<String, T> map, Attributes cmd, int tag,
            Association as) throws DicomServiceException {
        String cuid = cmd.getString(tag, null);
        T ret = service(map, cmd, cuid);
        if (ret != null)
            return ret;

        CommonExtendedNegotiation commonExtNeg =
                as.getCommonExtendedNegotiationFor(cuid);
        if (commonExtNeg != null) {
            for (String uid : commonExtNeg.getRelatedGeneralSOPClassUIDs()) {
                ret = service(map, cmd, uid);
                if (ret != null)
                    return ret;
            }
            ret = service(map, cmd, commonExtNeg.getServiceClassUID());
            if (ret != null)
                return ret;
       }

       ret = map.get("*");
       if (ret != null)
           return ret;

       throw new DicomServiceException(cmd, Status.NoSuchSOPclass);
    }

    private <T> T service(HashMap<String, T> map, Attributes cmd, String cuid)
            throws DicomServiceException {
        T ret = map != null ? map.get(cuid) : null;
        if (ret == null && sopCUIDs.contains(cuid))
            throw new DicomServiceException(cmd, Status.UnrecognizedOperation);
        return ret;
    }

    private void cstore(Association as, PresentationContext pc,
            Attributes cmd, PDVInputStream data) throws IOException {
        service(cstoreSCPs, cmd, Tag.AffectedSOPClassUID, as)
                .cstore(as, pc, cmd, data);
    }

    private void cget(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(cgetSCPs, cmd, Tag.AffectedSOPClassUID, as)
                .cget(as, pc, cmd, dataset);
    }

    private void cfind(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(cfindSCPs, cmd, Tag.AffectedSOPClassUID, as)
                .cfind(as, pc, cmd, dataset);
    }

    private void cmove(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(cmoveSCPs, cmd, Tag.AffectedSOPClassUID, as)
                .cmove(as, pc, cmd, dataset);
    }

    private void cecho(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(cechoSCPs, cmd, Tag.AffectedSOPClassUID, as)
                .cecho(as, pc, cmd);
    }

    private void nget(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(ngetSCPs, cmd, Tag.RequestedSOPClassUID, as)
                .nget(as, pc, cmd, dataset);
    }

    private void nset(Association as, PresentationContext pc, Attributes cmd,
            Attributes dataset) throws IOException {
        service(nsetSCPs, cmd, Tag.RequestedSOPClassUID, as)
                .nset(as, pc, cmd, dataset);
    }

    private void ncreate(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(ncreateSCPs, cmd, Tag.AffectedSOPClassUID, as)
                .ncreate(as, pc, cmd, dataset);
    }

    private void naction(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(nactionSCPs, cmd, Tag.RequestedSOPClassUID, as)
                .naction(as, pc, cmd, dataset);
    }

    private void ndelete(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(ndeleteSCPs, cmd, Tag.RequestedSOPClassUID, as)
                .ndelete(as, pc, cmd);
    }

    private void neventReport(Association as, PresentationContext pc,
            Attributes cmd, Attributes dataset) throws IOException {
        service(neventReportSCUs, cmd, Tag.AffectedSOPClassUID, as)
                .neventReport(as, pc, cmd, dataset);
    }

}
